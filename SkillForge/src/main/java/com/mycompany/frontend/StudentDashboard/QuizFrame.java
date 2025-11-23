package com.mycompany.frontend.StudentDashboard;

import com.mycompany.CourseManagement.CourseServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.CourseManagement.Quiz;
import com.mycompany.CourseManagement.Question;
import com.mycompany.CourseManagement.QuizServices;
import java.io.IOException;
import java.util.ArrayList;

public class QuizFrame extends JFrame {
    private ArrayList<ButtonGroup> buttonGroups;
    private JRadioButton[][] optionButtonsArray;
    private JButton nextButton;
    private JButton submitButton;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private Quiz currentQuiz;
    private ArrayList<Integer> userAnswers;
    private int currentQuestionIndex;
    private int score;
    private CourseDetailsFrame parentFrame;
    private String lessonId;
    private String studentId; // إضافة studentId كمتغير

    public QuizFrame(Quiz quiz, CourseDetailsFrame parentFrame, String lessonId) {
        this.currentQuiz = quiz;
        this.parentFrame = parentFrame;
        this.lessonId = lessonId;
        // جلب الـ studentId من الـ parentFrame
        this.studentId = (parentFrame != null) ? getStudentIdFromParent(parentFrame) : "s1";
        initializeQuiz();
        setupUI();
    }

    public QuizFrame(Quiz quiz) {
        this.currentQuiz = quiz;
        this.parentFrame = null;
        this.lessonId = null;
        this.studentId = "s1";
        initializeQuiz();
        setupUI();
    }

    public QuizFrame() {
        this.currentQuiz = createSampleQuiz();
        this.parentFrame = null;
        this.lessonId = null;
        this.studentId = "s1";
        initializeQuiz();
        setupUI();
    }

    // دالة مساعدة لجلب الـ studentId من الـ parentFrame
    private String getStudentIdFromParent(CourseDetailsFrame parentFrame) {
        try {
            // محاولة الوصول للـ student عبر reflection إذا كان متاحاً
            java.lang.reflect.Field studentField = parentFrame.getClass().getDeclaredField("student");
            studentField.setAccessible(true);
            Object student = studentField.get(parentFrame);
            if (student != null) {
                java.lang.reflect.Method getUserIdMethod = student.getClass().getMethod("getUserId");
                return (String) getUserIdMethod.invoke(student);
            }
        } catch (Exception e) {
            System.out.println("Could not get student ID from parent: " + e.getMessage());
        }
        return "s1"; // fallback
    }

    private void initializeQuiz() {
        this.userAnswers = new ArrayList<>();
        this.buttonGroups = new ArrayList<>();
        
        if (currentQuiz != null && currentQuiz.getTotalQuestions() > 0) {
            this.optionButtonsArray = new JRadioButton[currentQuiz.getTotalQuestions()][4];
            
            for (int i = 0; i < currentQuiz.getTotalQuestions(); i++) {
                userAnswers.add(-1);
                buttonGroups.add(new ButtonGroup());
            }
        } else {
            this.optionButtonsArray = new JRadioButton[0][4];
        }
        
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    private void setupUI() {
        setTitle("Course Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createQuestionPanels();
        
        add(mainPanel);
        cardLayout.first(mainPanel);
    }

    private void createQuestionPanels() {
        if (currentQuiz == null || currentQuiz.getTotalQuestions() == 0) {
            JPanel noQuestionsPanel = new JPanel(new BorderLayout());
            noQuestionsPanel.add(new JLabel("No questions available for this quiz", JLabel.CENTER), BorderLayout.CENTER);
            mainPanel.add(noQuestionsPanel, "noQuestions");
            return;
        }

        for (int i = 0; i < currentQuiz.getTotalQuestions(); i++) {
            JPanel questionPanel = createQuestionPanel(i);
            mainPanel.add(questionPanel, "question" + i);
        }
        
        JPanel resultsPanel = createResultsPanel();
        mainPanel.add(resultsPanel, "results");
    }

    private JPanel createQuestionPanel(int questionIndex) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Question question = currentQuiz.getQuestion(questionIndex);
        JLabel questionLabel = new JLabel("<html><body style='width: 300px'>" + 
            (questionIndex + 1) + ". " + question.getQuestionText() + "</body></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        ButtonGroup currentButtonGroup = buttonGroups.get(questionIndex);
        JRadioButton[] optionButtons = new JRadioButton[4];

        String[] currentOptions = question.getOptions();
        for (int j = 0; j < 4; j++) {
            optionButtons[j] = new JRadioButton(currentOptions[j]);
            optionButtons[j].setActionCommand(String.valueOf(j));
            currentButtonGroup.add(optionButtons[j]);
            optionsPanel.add(optionButtons[j]);
            optionButtonsArray[questionIndex][j] = optionButtons[j];
        }

        panel.add(optionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        if (questionIndex > 0) {
            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> showPreviousQuestion());
            buttonPanel.add(prevButton);
        }
        
        if (questionIndex < currentQuiz.getTotalQuestions() - 1) {
            nextButton = new JButton("Next");
            nextButton.addActionListener(e -> saveAndNext());
            buttonPanel.add(nextButton);
        } else {
            submitButton = new JButton("Submit Quiz");
            submitButton.addActionListener(e -> submitQuiz());
            buttonPanel.add(submitButton);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel resultsLabel = new JLabel("", JLabel.CENTER);
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(resultsLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton, BorderLayout.SOUTH);

        return panel;
    }

    private void saveAndNext() {
        saveCurrentAnswer();
        currentQuestionIndex++;
        cardLayout.next(mainPanel);
        displaySavedAnswer(currentQuestionIndex);
    }

    private void showPreviousQuestion() {
        saveCurrentAnswer();
        currentQuestionIndex--;
        cardLayout.show(mainPanel, "question" + currentQuestionIndex);
        displaySavedAnswer(currentQuestionIndex);
    }

    private void saveCurrentAnswer() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= userAnswers.size()) return;
        
        ButtonModel selectedModel = buttonGroups.get(currentQuestionIndex).getSelection();
        if (selectedModel != null) {
            int selectedIndex = Integer.parseInt(selectedModel.getActionCommand());
            userAnswers.set(currentQuestionIndex, selectedIndex);
        } else {
            userAnswers.set(currentQuestionIndex, -1);
        }
    }

    private void displaySavedAnswer(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= userAnswers.size()) return;
        
        int savedAnswer = userAnswers.get(questionIndex);
        if (savedAnswer != -1) {
            buttonGroups.get(questionIndex).clearSelection();
            if (savedAnswer >= 0 && savedAnswer < 4) {
                optionButtonsArray[questionIndex][savedAnswer].setSelected(true);
            }
        }
    }

    private void submitQuiz() {
        saveCurrentAnswer();
        calculateScore();
        showResults();
    }

    private void calculateScore() {
        this.score = 0;
        if (currentQuiz == null) return;
        
        for (int i = 0; i < currentQuiz.getTotalQuestions(); i++) {
            if (i < userAnswers.size()) {
                int userAnswer = userAnswers.get(i);
                int correctAnswer = currentQuiz.getQuestion(i).getCorrectOptionIndex();
                
                System.out.println("Q" + i + ": User=" + userAnswer + ", Correct=" + correctAnswer + " -> " + (userAnswer == correctAnswer ? "CORRECT" : "WRONG"));
                
                if (userAnswer != -1 && userAnswer == correctAnswer) {
                    score++;
                }
            }
        }
        System.out.println("Final Score: " + score + "/" + currentQuiz.getTotalQuestions());
    }

    private void showResults() {
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(this, "Quiz data is missing!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        boolean passed = currentQuiz.isPassed(score);
        double percentage = currentQuiz.calculatePercentage(score);
        
        String message = String.format(
            "Quiz Completed!\n\nScore: %d/%d (%.1f%%)\n\n%s",
            score, currentQuiz.getTotalQuestions(), percentage,
            passed ? "Congratulations! You passed!" : "Try again!"
        );
        
        System.out.println("=== QUIZ FINAL RESULTS ===");
        System.out.println("Student: " + studentId);
        System.out.println("Score: " + score + "/" + currentQuiz.getTotalQuestions());
        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Passed: " + passed);
        System.out.println("Lesson ID: " + lessonId);
        
        try {
            // استخدام الـ studentId الفعلي بدل الثابت
            QuizServices.submitQuizResult(studentId, currentQuiz.getQuizId(), score, passed);
            
            if (passed) {
                String actualLessonId = (this.lessonId != null) ? this.lessonId : currentQuiz.getLessonId().replace("QZ", "L");
                System.out.println("Completing lesson: " + actualLessonId);
                
                CourseServices.completeLessonViaQuiz(studentId, actualLessonId);
                
                if (parentFrame != null) {
       
                    parentFrame.refreshCourseDetails();
           
         
                    
                    JOptionPane.showMessageDialog(this, 
                        "✅ Quiz passed! You can now mark the lesson as complete.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error saving quiz results: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error saving results: " + ex.getMessage());
        }
        
        JOptionPane.showMessageDialog(this, message, "Quiz Results", 
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }

    // دالة مساعدة لجلب الـ courseId من الـ lessonId
    private String getCourseIdFromLesson(String lessonId) {
        try {
            // محاولة الوصول للـ course من الـ parentFrame
            if (parentFrame != null) {
                java.lang.reflect.Field courseField = parentFrame.getClass().getDeclaredField("course");
                courseField.setAccessible(true);
                Object course = courseField.get(parentFrame);
                if (course != null) {
                    java.lang.reflect.Method getCourseIdMethod = course.getClass().getMethod("getCourseId");
                    return (String) getCourseIdMethod.invoke(course);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not get course ID: " + e.getMessage());
        }
        return null;
    }

    public static void startQuiz() {
        SwingUtilities.invokeLater(() -> {
            new QuizFrame().setVisible(true);
        });
    }

    public static void startQuiz(Quiz quiz) {
        SwingUtilities.invokeLater(() -> {
            new QuizFrame(quiz).setVisible(true);
        });
    }

    public static void startQuiz(Quiz quiz, CourseDetailsFrame parentFrame, String lessonId) {
        SwingUtilities.invokeLater(() -> {
            new QuizFrame(quiz, parentFrame, lessonId).setVisible(true);
        });
    }

    private Quiz createSampleQuiz() {
        try {
            ArrayList<Question> questions = new ArrayList<>();
            
            questions.add(QuizServices.createQuestion(
                "What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Madrid"},
                2,
                "Paris is the capital of France"
            ));
            
            questions.add(QuizServices.createQuestion(
                "Which programming language is this?",
                new String[]{"Python", "C++", "Java", "JavaScript"},
                2,
                "This application is built using Java"
            ));
            
            questions.add(QuizServices.createQuestion(
                "What is 2 + 2?",
                new String[]{"3", "4", "5", "6"},
                1,
                "Basic arithmetic: 2 + 2 = 4"
            ));
            
            return QuizServices.createQuiz("L1", questions);
            
        } catch (IOException e) {
            return new Quiz("L1");
        }
    }
}