/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.instructor;

import com.mycompany.CourseManagement.Lesson;
import com.mycompany.studentdashboard.CourseManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author HP
 */
public class LessonPanel {
     private Lesson lesson;
    private final CourseManager courseManager;
    private final JLabel lblLessonTitle = new JLabel();
    private final JTextArea txtLessonContent = new JTextArea(10, 40);
    private final JButton btnEditLesson = new JButton("Edit Lesson");
    private final JButton btnDeleteLesson = new JButton("Delete Lesson");

    public interface LessonChangeCallback {
        void onLessonDeleted(String lessonId);
        void onLessonEdited(Lesson lesson);
    }
    private LessonChangeCallback callback;

    public LessonPanel(CourseManager courseManager) {
        this.courseManager = courseManager;
        initUI();
        attachActions();
        setVisible(false);
    }

    private void initUI() {
        setLayout(new BorderLayout(8,8));
        lblLessonTitle.setFont(lblLessonTitle.getFont().deriveFont(Font.BOLD, 16f));
        add(lblLessonTitle, BorderLayout.NORTH);

        txtLessonContent.setLineWrap(true);
        txtLessonContent.setWrapStyleWord(true);
        txtLessonContent.setEditable(false);
        add(new JScrollPane(txtLessonContent), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        bottom.add(btnEditLesson);
        bottom.add(btnDeleteLesson);
        add(bottom, BorderLayout.SOUTH);
    }

    private void attachActions() {
        btnEditLesson.addActionListener(e -> {
            if (lesson == null) return;
            JTextField titleF = new JTextField(lesson.getTitle());
            JTextArea contentA = new JTextArea(8, 40);
            contentA.setText(lesson.getContent());
            Object[] msg = {"Title:", titleF, "Content:", new JScrollPane(contentA)};
            int opt = JOptionPane.showConfirmDialog(this, msg, "Edit Lesson", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                String newTitle = titleF.getText().trim();
                String newContent = contentA.getText().trim();
                if (newTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Title required.");
                    return;
                }
                lesson.setTitle(newTitle);
                lesson.setContent(newContent);
                
                if (callback != null) callback.onLessonEdited(lesson);
                JOptionPane.showMessageDialog(this, "Lesson edited.");
            }
        });

        btnDeleteLesson.addActionListener(e -> {
            if (lesson == null) return;
            int opt = JOptionPane.showConfirmDialog(this, "Delete lesson \"" + lesson.getTitle() + "\"?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                if (callback != null) callback.onLessonDeleted(lesson.getLessonId());
            }
        });
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        if (lesson == null) {
            lblLessonTitle.setText("");
            txtLessonContent.setText("");
            setVisible(false);
            return;
        }
        lblLessonTitle.setText(lesson.getTitle());
        txtLessonContent.setText(lesson.getContent());
        setVisible(true);
    }

    public void setCallback(LessonChangeCallback cb) {
        this.callback = cb;
    }
}
