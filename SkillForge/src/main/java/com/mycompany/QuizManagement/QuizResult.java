package com.mycompany.QuizManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a student's quiz attempt result
 */
public class QuizResult {

    private String lessonId;
    private int score;
    private boolean passed;
    private String attemptDate;
    private int attemptNumber;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @JsonProperty(defaultValue = "[]")
    private ArrayList<Integer> studentAnswers; // Indices of selected answers

    public QuizResult() {
        this.attemptDate = LocalDateTime.now().format(FORMATTER);
        this.studentAnswers = new ArrayList<>();
    }

    public QuizResult(String lessonId, int score, boolean passed, int attemptNumber,
            ArrayList<Integer> studentAnswers) {
        this.lessonId = lessonId;
        this.score = score;
        this.passed = passed;
        this.attemptDate = LocalDateTime.now().format(FORMATTER);
        this.attemptNumber = attemptNumber;
        this.studentAnswers = studentAnswers != null ? studentAnswers : new ArrayList<>();
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(String attemptDate) {
        this.attemptDate = attemptDate;
    }

    /**
     * Get attempt date as LocalDateTime object
     */
    @JsonIgnore
    public LocalDateTime getAttemptDateTime() {
        return LocalDateTime.parse(attemptDate, FORMATTER);
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public ArrayList<Integer> getStudentAnswers() {
        if (studentAnswers == null) {
            studentAnswers = new ArrayList<>();
        }
        return studentAnswers;
    }

    public void setStudentAnswers(ArrayList<Integer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    @Override
    public String toString() {
        return String.format("QuizResult[LessonId=%s, Score=%d%%, Passed=%b, Attempt=%d, Date=%s]",
                lessonId, score, passed, attemptNumber, attemptDate);
    }
}