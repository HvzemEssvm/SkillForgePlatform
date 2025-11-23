/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.frontend.InstructorDashboard;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.Status;
import com.mycompany.UserAccountManagement.Instructor;
import com.mycompany.frontend.Main.MainFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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
    private JButton btnAddQuiz;
    private JButton btnLogout;
    private InstructorDashboardFrame parent;
    private Instructor instructor;

    public DashboardPanel(InstructorDashboardFrame parent, Instructor instructor) {
        this.parent = parent;
        this.instructor = instructor;

        setLayout(new BorderLayout());

        // Create list for courses
        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(200, 500));
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnCreateCourse = new JButton("Create New Course");
        btnCreateCourse.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnAddQuiz = new JButton("Add Quiz");
        btnAddQuiz.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        
        buttonPanel.add(btnCreateCourse);
        buttonPanel.add(btnAddQuiz);
        buttonPanel.add(btnLogout);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load courses
        loadCourses();

        // Add list selection listener
        courseList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = courseList.getSelectedValue();
                    if (selectedValue != null) {
                        String selectedTitle = selectedValue.split("-")[0].trim();
                        try {
                            ArrayList<Course> courses = instructor.getMyCourses();
                            for (Course c : courses) {
                                if (c.getTitle().equals(selectedTitle)) {
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

        // Create course button action
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

        // Add Quiz button action - الإصدارة البسيطة
        btnAddQuiz.addActionListener(e -> {
            // تحقق أولاً إذا فيه كورسات
            try {
                ArrayList<Course> courses = instructor.getMyCourses();
                if (courses.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "You need to create a course first!");
                    return;
                }
                
                // فتح واجهة إضافة الكويز
                AddQuizWizard wizard = new AddQuizWizard(
                    (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this), 
                    instructor
                );
                wizard.setVisible(true);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Logout button action
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                parent.dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                JOptionPane.showMessageDialog(mainFrame, "Logged Out Successfully", "Successful Operation!", JOptionPane.INFORMATION_MESSAGE);
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
                courseListModel.addElement(c.getTitle() + " - " + c.getStatus().toString());
            }
            
            // إذا مفيش كورسات، نعطل زر إضافة الكويز
            btnAddQuiz.setEnabled(!courses.isEmpty());
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading courses: " + ex.getMessage());
            btnAddQuiz.setEnabled(false);
        }
    }
}