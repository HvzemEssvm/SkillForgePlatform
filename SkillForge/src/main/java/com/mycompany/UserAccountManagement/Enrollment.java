package com.mycompany.UserAccountManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.QuizManagement.QuizResult;

import java.util.ArrayList;

/**
 * Enrollment entity with quiz tracking
 * @author Lab8_Team
 */
public class Enrollment {

    private String courseId;
    
    @JsonProperty(defaultValue = "[]")
    private ArrayList<String> completedLessons;
    
    @JsonProperty(defaultValue = "[]")
    private ArrayList<QuizResult> quizResults; // NEW: Track all quiz attempts
    
    private String completionPercentage; // e.g., "70%"
    
    private double averageScore; // Average score across all quizzes

    // Default constructor for Jackson deserialization
    public Enrollment() {
        this.completedLessons = new ArrayList<>();
        this.quizResults = new ArrayList<>();
        this.completionPercentage = "0%";
        this.averageScore = 0.0;
    }

    public Enrollment(String courseId, ArrayList<String> completedLessons) {
        this.courseId = courseId;
        this.completedLessons = completedLessons != null ? completedLessons : new ArrayList<>();
        this.quizResults = new ArrayList<>();
        this.completionPercentage = "0%";
        this.averageScore = 0.0;
    }

    /**
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @return the completedLessons
     */
    public ArrayList<String> getCompletedLessons() {
        if (completedLessons == null) {
            completedLessons = new ArrayList<>();
        }
        return completedLessons;
    }

    /**
     * @return the quizResults
     */
    public ArrayList<QuizResult> getQuizResults() {
        if (quizResults == null) {
            quizResults = new ArrayList<>();
        }
        return quizResults;
    }

    /**
     * @param quizResults the quizResults to set
     */
    public void setQuizResults(ArrayList<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    /**
     * @return the completionPercentage
     */
    public String getCompletionPercentage() {
        return completionPercentage;
    }

    /**
     * @param completionPercentage the completionPercentage to set
     */
    public void setCompletionPercentage(String completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    /**
     * @return the averageScore
     */
    public double getAverageScore() {
        return averageScore;
    }

    /**
     * @param averageScore the averageScore to set
     */
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
    
    /**
     * Add a quiz result to this enrollment
     * @param result the quiz result to add
     */
    public void addQuizResult(QuizResult result) {
        if (quizResults == null) {
            quizResults = new ArrayList<>();
        }
        quizResults.add(result);
    }
}