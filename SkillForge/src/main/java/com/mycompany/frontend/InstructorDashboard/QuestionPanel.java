/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.frontend.InstructorDashboard;

import javax.swing.*;
import java.awt.*;
import com.mycompany.CourseManagement.Question;
import com.mycompany.CourseManagement.QuizServices;

public class QuestionPanel extends JPanel {
    private JTextField questionText;
    private JTextField[] optionFields;
    private JComboBox<String> correctAnswerCombo;
    private JTextField explanationField;
    private int questionNumber;

    public QuestionPanel(int questionNumber) {
        this.questionNumber = questionNumber;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Question " + questionNumber));
        setBackground(new Color(240, 240, 240));

        // Panel للسؤال
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel questionLabel = new JLabel("Question Text:");
        questionText = new JTextField();
        questionText.setPreferredSize(new Dimension(400, 30));
        
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(questionText, BorderLayout.CENTER);

        // Panel للاختيارات
        JPanel optionsPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        
        optionFields = new JTextField[4];
        String[] optionLabels = {"Option A:", "Option B:", "Option C:", "Option D:"};
        
        for (int i = 0; i < 4; i++) {
            optionsPanel.add(new JLabel(optionLabels[i]));
            optionFields[i] = new JTextField();
            optionsPanel.add(optionFields[i]);
        }

        // Panel للإجابة الصحيحة والشرح
        JPanel settingsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        
        settingsPanel.add(new JLabel("Correct Answer:"));
        correctAnswerCombo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        settingsPanel.add(correctAnswerCombo);
        
        settingsPanel.add(new JLabel("Explanation:"));
        explanationField = new JTextField();
        settingsPanel.add(explanationField);

        // زر لحذف السؤال
        JButton btnRemove = new JButton("🗑️ Remove");
        btnRemove.addActionListener(e -> removeQuestion());

        // Panel رئيسي
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(questionPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(settingsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnRemove);

        add(mainPanel, BorderLayout.CENTER);
    }

    public Question getQuestion() {
        // التحقق من أن كل الحقول مملوءة
        if (questionText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter question text for question " + questionNumber);
            return null;
        }

        for (int i = 0; i < 4; i++) {
            if (optionFields[i].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter all 4 options for question " + questionNumber);
                return null;
            }
        }

        if (explanationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter explanation for question " + questionNumber);
            return null;
        }

        try {
            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                options[i] = optionFields[i].getText().trim();
            }

            int correctIndex = correctAnswerCombo.getSelectedIndex();
            
            return QuizServices.createQuestion(
                questionText.getText().trim(),
                options,
                correctIndex,
                explanationField.getText().trim()
            );
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating question: " + e.getMessage());
            return null;
        }
    }

    private void removeQuestion() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }
}
