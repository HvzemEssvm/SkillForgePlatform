/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Analytics;

/**
 *
 * @author Zeyad
 */
public class LessonAnalytics {
    private String lessonId;
    private String lessonTitle;
    private int studentsCompleted;
    private int studentsAttempted; // Attempted quiz
    private double averageScore;
    private int totalAttempts;
    private double passRate; // Percentage of students who passed

    public LessonAnalytics() {
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public int getStudentsCompleted() {
        return studentsCompleted;
    }

    public void setStudentsCompleted(int studentsCompleted) {
        this.studentsCompleted = studentsCompleted;
    }

    public int getStudentsAttempted() {
        return studentsAttempted;
    }

    public void setStudentsAttempted(int studentsAttempted) {
        this.studentsAttempted = studentsAttempted;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public double getPassRate() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate = passRate;
    }
}
