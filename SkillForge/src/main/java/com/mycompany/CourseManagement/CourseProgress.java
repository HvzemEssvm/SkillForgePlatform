/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class CourseProgress {
    
    private Map<String, String> lessonStatus = new HashMap<>(); //passed or incompleted

    
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

    private Boolean completed = false;

    private String certificateId; 

   
    public Map<String, String> getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(Map<String, String> lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    
    public void updateLessonStatus(String lessonId, boolean passed) {
        lessonStatus.put(lessonId, passed ? "PASSED" : "INCOMPLETE");
        checkCourseCompletion();
    }

 
    private void checkCourseCompletion() {
        
        for (String status : lessonStatus.values()) {
            if (!status.equals("PASSED")) {
                completed = false;
                return;
            }
        }
        completed = true;
    }

    
    public void addQuizAttempt(QuizAttempt attempt) {
        quizAttempts.add(attempt);
        
        updateLessonStatus(attempt.getLessonId(), attempt.isPassed());
    }
}

    

