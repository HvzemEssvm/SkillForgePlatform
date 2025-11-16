/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.instructor;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.Lesson;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author HP
 */
public class LessonPanel {
    
    private MainFrame parent;
    private Lesson lesson;
    private Course course;

    public LessonPanel(MainFrame parent, Lesson lesson, Course course) {
        this.parent = parent;
        this.lesson = lesson;
        this.course = course;

        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel(lesson.getTitle());
        JTextArea txtContent = new JTextArea(lesson.getContent());
        txtContent.setEditable(false);

        JButton btnBack = new JButton("Back to Course");

        add(lblTitle, BorderLayout.NORTH);
        add(new JScrollPane(txtContent), BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> parent.showCoursePanel(course));
    }
    
}
