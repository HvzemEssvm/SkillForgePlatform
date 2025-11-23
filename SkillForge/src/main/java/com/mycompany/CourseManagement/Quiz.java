package com.mycompany.CourseManagement;

import java.util.ArrayList;

public class Quiz {
    private String quizId;
    private String lessonId;
    private ArrayList<Question> questions;
    private int passingScore = 70; // النسبة المئوية للنجاح
    private int timeLimit; // بالدقائق
    private int maxAttempts = 3; // عدد المحاولات المسموح

    public Quiz() {
        this.questions = new ArrayList<>();
    }

public Quiz(String lessonId) {
    this();
    // نجبر الـ Quiz ID يكون مربوط بالـ Lesson ID
    this.quizId = "QUIZ_" + lessonId + "_" + System.currentTimeMillis();
    this.lessonId = lessonId;
    System.out.println("Created quiz: " + this.quizId + " for lesson: " + this.lessonId);
}

    // Getters and Setters
    public String getQuizId() { return quizId; }
    public String getLessonId() { return lessonId; }
    public ArrayList<Question> getQuestions() { return questions; }
    public int getPassingScore() { return passingScore; }
    public int getTimeLimit() { return timeLimit; }
    public int getMaxAttempts() { return maxAttempts; }

    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }

    // Methods لإدارة الأسئلة
    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(String questionId) {
        questions.removeIf(q -> q.getQuestionId().equals(questionId));
    }

    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int calculateScore(ArrayList<Integer> userAnswers) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < userAnswers.size() && userAnswers.get(i) != -1) {
                int userAnswer = userAnswers.get(i);
                int correctAnswer = questions.get(i).getCorrectOptionIndex();
                
                System.out.println("DEBUG: Q" + i + " - User: " + userAnswer + ", Correct: " + correctAnswer);
                
                if (userAnswer == correctAnswer) {
                    score++;
                }
            }
        }
        return score;
    }

    public boolean isPassed(int score) {
        if (questions.isEmpty()) return false;
        double percentage = (double) score / questions.size() * 100;
        System.out.println("Score: " + score + "/" + questions.size() + " = " + percentage + "% - Passing: " + passingScore + "%");
        return percentage >= passingScore;
    }

    public double calculatePercentage(int score) {
        if (questions.isEmpty()) return 0;
        return (double) score / questions.size() * 100;
    }
}