/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.instructor;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.studentdashboard.CourseManager;
import java.awt.BorderLayout;
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

    private JLabel lblCourseTitle;
    private JButton btnEditCourse;
    private JButton btnDeleteCourse;
    private JButton btnCreateLesson;
    private JList<String> lessonList;
    private DefaultListModel<String> lessonListModel;

    public CoursePanel(MainFrame parent, Course course) {
        this.parent = parent;
        this.course = course;

        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        lblCourseTitle = new JLabel(course.getTitle());
        btnEditCourse = new JButton("Edit Course");
        btnDeleteCourse = new JButton("Delete Course");
        btnCreateLesson = new JButton("Create Lesson");

        topPanel.add(lblCourseTitle);
        topPanel.add(btnEditCourse);
        topPanel.add(btnDeleteCourse);
        topPanel.add(btnCreateLesson);

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
                    for (Lesson l : course.getLessons()) {
                        if (l.getTitle().equals(selected)) {
                            parent.showLessonPanel(l, course);
                            break;
                        }
                    }
                }
            }
        });

        
        btnCreateLesson.addActionListener(e -> {
            try {
                Lesson l = new Lesson("New Lesson", "Content");
                course.addLesson(l);
                loadLessons();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error creating lesson: " + ex.getMessage());
            }
        });
    }

    private void loadLessons() {
        lessonListModel.clear();
        for (Lesson l : course.getLessons()) {
            lessonListModel.addElement(l.getTitle());
        }
    }
}
