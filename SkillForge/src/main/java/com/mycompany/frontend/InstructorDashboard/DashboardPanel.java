/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.frontend.InstructorDashboard;

import com.mycompany.CourseManagement.Course;
import com.mycompany.UserAccountManagement.Instructor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author HP
 */
public class DashboardPanel extends JPanel {
    private JList<String> courseList;
    private DefaultListModel<String> courseListModel;
    private JButton btnCreateCourse;
    private InstructorDashboardFrame parent;
    private Instructor instructor;

    public DashboardPanel(InstructorDashboardFrame parent, Instructor instructor) {
        this.parent = parent;
        this.instructor = instructor;

        setLayout(new BorderLayout());

        
        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(200, 500));
        add(scrollPane, BorderLayout.CENTER);

      
        btnCreateCourse = new JButton("Create New Course");
        add(btnCreateCourse, BorderLayout.SOUTH);

        
        loadCourses();

        
        courseList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selected = courseList.getSelectedValue();
                    if (selected != null) {
                        try {
                            ArrayList<Course> courses = instructor.getMyCourses();
                            for (Course c : courses) {
                                if (c.getTitle().equals(selected)) {
                                    parent.showCoursePanel(c);
                                    break;
                                }
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error loading course: " + ex.getMessage());
                        }
                    }
                }
            }
        });


        btnCreateCourse.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Enter course title:");
            if (title != null && !title.trim().isEmpty()) {
                String description = JOptionPane.showInputDialog(this, "Enter course description:");
                if (description != null) {
                    try {
                        instructor.createCourse(title, description);
                        loadCourses();
                        JOptionPane.showMessageDialog(this, "Course created successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error creating course: " + ex.getMessage());
                    }
                }
            }
        });
    }

    public void refreshCourses() {
        loadCourses();
    }

    private void loadCourses() {
        courseListModel.clear();
        try {
            ArrayList<Course> courses = instructor.getMyCourses();
            for (Course c : courses) {
                courseListModel.addElement(c.getTitle());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading courses: " + ex.getMessage());
        }
    }
}
