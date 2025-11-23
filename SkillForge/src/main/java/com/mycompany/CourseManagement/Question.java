/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

/**
 *
 * @author afifi.store
 */


public class Question {
    private String questionId;
    private String questionText;
    private String[] options;
    private int correctOptionIndex;
    private String explanation;

    public Question() {
    }

    public Question(String questionText, String[] options, int correctOptionIndex, String explanation) {
        this.questionId = "Q" + generateNextQuestionId();
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
    }

    private int generateNextQuestionId() {
        // دي هنكملها بعدين علشان نعمل ID فريد
        return (int) (Math.random() * 1000); // مؤقت
    }

    // Getters and Setters
    public String getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public String getExplanation() { return explanation; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setOptions(String[] options) { this.options = options; }
    public void setCorrectOptionIndex(int correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    // علشان نcheck الإجابة
    public boolean isCorrect(int selectedOptionIndex) {
        return selectedOptionIndex == correctOptionIndex;
    }

    public String getCorrectAnswer() {
        return options[correctOptionIndex];
    }
}