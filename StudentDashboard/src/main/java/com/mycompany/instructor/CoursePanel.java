/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.instructor;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.UserAccountManagement.Instructor;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author HP
 */
public class CoursePanel extends JPanel{
    private MainFrame parent;
    private Course course;
    private Instructor instructor;

    private JLabel lblCourseTitle;
    private JLabel lblCourseDescription;
    private JButton btnEditCourse;
    private JButton btnDeleteCourse;
    private JButton btnCreateLesson;
    private JButton btnViewStudents;
    private JButton btnBack;
    private JList<String> lessonList;
    private DefaultListModel<String> lessonListModel;

    public CoursePanel(MainFrame parent, Course course, Instructor instructor) {
        this.parent = parent;
        this.course = course;
        this.instructor = instructor;

        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new java.awt.GridLayout(3, 1));

        JPanel titlePanel = new JPanel();
        lblCourseTitle = new JLabel("Course: " + course.getTitle());
        lblCourseDescription = new JLabel("Description: " + course.getDescription());
        titlePanel.add(lblCourseTitle);

        JPanel buttonPanel1 = new JPanel();
        btnBack = new JButton("Back to Dashboard");
        btnEditCourse = new JButton("Edit Course");
        btnDeleteCourse = new JButton("Delete Course");
        buttonPanel1.add(btnBack);
        buttonPanel1.add(btnEditCourse);
        buttonPanel1.add(btnDeleteCourse);

        JPanel buttonPanel2 = new JPanel();
        btnCreateLesson = new JButton("Create Lesson");
        btnViewStudents = new JButton("View Enrolled Students");
        buttonPanel2.add(btnCreateLesson);
        buttonPanel2.add(btnViewStudents);

        topPanel.add(titlePanel);
        topPanel.add(buttonPanel1);
        topPanel.add(buttonPanel2);

        add(topPanel, BorderLayout.NORTH);


        lessonListModel = new DefaultListModel<>();
        lessonList = new JList<>(lessonListModel);
        JScrollPane scrollPane = new JScrollPane(lessonList);
        add(scrollPane, BorderLayout.CENTER);

        loadLessons();


        lessonList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = lessonList.getSelectedValue();
                if (selected != null) {
                    try {
                        ArrayList<Lesson> lessons = instructor.getCourseLessons(course.getCourseId());
                        for (Lesson l : lessons) {
                            if (l.getTitle().equals(selected)) {
                                parent.showLessonPanel(l, course);
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error loading lesson: " + ex.getMessage());
                    }
                }
            }
        });

        btnBack.addActionListener(e -> parent.showDashboard());

        btnCreateLesson.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Enter lesson title:");
            if (title != null && !title.trim().isEmpty()) {
                String content = JOptionPane.showInputDialog(this, "Enter lesson content:");
                if (content != null) {
                    try {
                        Lesson l = instructor.createLesson(title, content);
                        instructor.uploadLesson(l, course.getCourseId());
                        loadLessons();
                        JOptionPane.showMessageDialog(this, "Lesson created successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error creating lesson: " + ex.getMessage());
                    }
                }
            }
        });

        btnEditCourse.addActionListener(e -> {
            String newTitle = JOptionPane.showInputDialog(this, "Enter new course title:", course.getTitle());
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                String newDescription = JOptionPane.showInputDialog(this, "Enter new course description:", course.getDescription());
                if (newDescription != null) {
                    try {
                        instructor.updateCourse(course.getCourseId(), newTitle, newDescription);
                        course.setTitle(newTitle);
                        course.setDescription(newDescription);
                        lblCourseTitle.setText("Course: " + newTitle);
                        lblCourseDescription.setText("Description: " + newDescription);
                        JOptionPane.showMessageDialog(this, "Course updated successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error updating course: " + ex.getMessage());
                    }
                }
            }
        });

        btnDeleteCourse.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this course? This action cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    instructor.deleteCourse(course.getCourseId());
                    JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                    parent.showDashboard();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting course: " + ex.getMessage());
                }
            }
        });

        btnViewStudents.addActionListener(e -> {
            try {
                ArrayList<String> students = instructor.getEnrolledStudents(course.getCourseId());
                if (students.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No students enrolled in this course yet.");
                } else {
                    StringBuilder studentList = new StringBuilder("Enrolled Students:\n\n");
                    for (String studentId : students) {
                        studentList.append("- ").append(studentId).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, studentList.toString());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage());
            }
        });
    }

    private void loadLessons() {
        lessonListModel.clear();
        try {
            ArrayList<Lesson> lessons = instructor.getCourseLessons(course.getCourseId());
            for (Lesson l : lessons) {
                lessonListModel.addElement(l.getTitle());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading lessons: " + ex.getMessage());
        }
    }
}
