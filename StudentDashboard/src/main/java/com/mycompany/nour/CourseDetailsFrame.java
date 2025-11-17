package com.mycompany.nour;

import javax.swing.*;
import java.awt.*;
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.UserAccountManagement.Student;
import java.util.ArrayList;

public class CourseDetailsFrame extends JPanel {
    private Course course;
    private Student student;
    private Runnable backAction;

    public CourseDetailsFrame(Course course, Student student, Runnable backAction) {
        this.course = course;
        this.student = student;
        this.backAction = backAction;

        createCourseDetails();
    }

    private void createCourseDetails() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(50, 110, 160));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> backAction.run());
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleHeader = new JLabel("Course Details", JLabel.CENTER);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeader.setForeground(Color.WHITE);
        headerPanel.add(titleHeader, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Course info panel with enhanced styling
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)
        ));

        JLabel titleLabel = new JLabel("Title: " + course.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel instructorLabel = new JLabel("Instructor: " + course.getInstructorId());
        instructorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructorLabel.setForeground(new Color(70, 70, 70));

        JLabel descLabel = new JLabel("<html><b>Description:</b> " + course.getDescription() + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(new Color(70, 70, 70));

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(instructorLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(descLabel);

        // Lessons panel with enhanced styling
        JPanel lessonsContainer = new JPanel(new BorderLayout());
        lessonsContainer.setBackground(new Color(245, 245, 250));
        lessonsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lessonsTitle = new JLabel("Lessons");
        lessonsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lessonsTitle.setForeground(new Color(50, 50, 50));
        lessonsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        lessonsContainer.add(lessonsTitle, BorderLayout.NORTH);

        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(new Color(245, 245, 250));

        try {
            ArrayList<Lesson> lessons = student.getCourseLessons(course.getCourseId());

            if (lessons.isEmpty()) {
                JLabel emptyLabel = new JLabel("No lessons found");
                emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                emptyLabel.setForeground(Color.GRAY);
                lessonsPanel.add(emptyLabel);
            } else {
                for (Lesson lesson : lessons) {
                    JPanel lessonPanel = createLessonPanel(lesson);
                    lessonsPanel.add(lessonPanel);
                    lessonsPanel.add(Box.createVerticalStrut(10));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading lessons: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            lessonsPanel.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        lessonsContainer.add(scrollPane, BorderLayout.CENTER);

        // Combined panel for info and lessons
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setBackground(new Color(245, 245, 250));
        contentPanel.add(infoPanel, BorderLayout.NORTH);
        contentPanel.add(lessonsContainer, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createLessonPanel(Lesson lesson) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(700, 80));

        JLabel titleLabel = new JLabel(lesson.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(new Color(40, 40, 40));

        JButton contentButton = new JButton("View Content");
        contentButton.setFont(new Font("Arial", Font.PLAIN, 12));
        contentButton.setBackground(new Color(70, 130, 180));
        contentButton.setForeground(Color.WHITE);
        contentButton.setFocusPainted(false);
        contentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        contentButton.addActionListener(e -> {
            showLessonContent(lesson);
        });

        JButton completeButton = new JButton(
            lesson.isCompleted() ? "✓ Completed" : "Mark Complete"
        );
        completeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        completeButton.setEnabled(!lesson.isCompleted());
        completeButton.setFocusPainted(false);
        completeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        completeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        if (lesson.isCompleted()) {
            completeButton.setBackground(new Color(40, 167, 69));
            completeButton.setForeground(Color.WHITE);
        } else {
            completeButton.setBackground(new Color(240, 240, 240));
            completeButton.setForeground(new Color(60, 60, 60));
        }

        completeButton.addActionListener(e -> {
            try {
                student.completeLesson(lesson.getLessonId());
                completeButton.setEnabled(false);
                completeButton.setText("✓ Completed");
                completeButton.setBackground(new Color(40, 167, 69));
                completeButton.setForeground(Color.WHITE);
                JOptionPane.showMessageDialog(this, "Lesson Completed: " + lesson.getTitle());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error completing lesson: " + ex.getMessage());
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(contentButton);
        buttonPanel.add(completeButton);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void showLessonContent(Lesson lesson) {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Header with back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton backButton = new JButton("← Back to Course");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(50, 110, 160));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            removeAll();
            createCourseDetails();
            revalidate();
            repaint();
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleHeader = new JLabel(lesson.getTitle(), JLabel.CENTER);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeader.setForeground(Color.WHITE);
        headerPanel.add(titleHeader, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Content area with enhanced styling
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBackground(new Color(245, 245, 250));
        contentContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JTextArea contentArea = new JTextArea(lesson.getContent());
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 15));
        contentArea.setForeground(new Color(50, 50, 50));
        contentArea.setBackground(Color.WHITE);
        contentArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        contentContainer.add(contentPanel, BorderLayout.CENTER);
        add(contentContainer, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
