package com.mycompany.QuizManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Question entity for quiz
 */
public class Question {

    private String questionText;

    @JsonProperty(defaultValue = "[]")
    private ArrayList<String> options; // Multiple choice options

    private int correctAnswerIndex; // Index of correct answer in options array

    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(String questionText, ArrayList<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options != null ? options : new ArrayList<>();
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getOptions() {
        if (options == null) {
            options = new ArrayList<>();
        }
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    /**
     * Check if the provided answer is correct
     * 
     * @param answerIndex the index of selected answer
     * @return true if correct, false otherwise
     */
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    @Override
    public String toString() {
        return String.format("Question[Text='%s', Options=%d, CorrectAnswer=%d]",
                questionText, options.size(), correctAnswerIndex);
    }
}