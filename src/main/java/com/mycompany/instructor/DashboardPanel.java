/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.instructor;

import com.mycompany.CourseManagement.Course;
import com.mycompany.studentdashboard.CourseManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
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
    private final MainFrame parent;

    public DashboardPanel(MainFrame parent) {
        this.parent = parent;

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
                        
                        Course c = CourseManager.getCourseByName(selected);
                        parent.showCoursePanel(c);
                    }
                }
            }
        });

        
        btnCreateCourse.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                
                Course c = new Course("New Course");
                CourseManager.addCourse(c);

                
                loadCourses();
            }
        });
    }

    
    private void loadCourses() {
        courseListModel.clear();
        for (Course c : CourseManager.getAllCourses()) {
            courseListModel.addElement(c.getName());
        }
    }
    
}
