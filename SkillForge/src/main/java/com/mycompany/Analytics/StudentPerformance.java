/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Analytics;

import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class StudentPerformance {
    private String studentId;
    private String studentName;
    private int completedLessons;
    private int totalLessons;
    private double completionPercentage;
    private double averageScore;
    private int totalQuizAttempts;
    private ArrayList<LessonProgress> lessonProgress;

    public StudentPerformance() {
        this.lessonProgress = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getTotalQuizAttempts() {
        return totalQuizAttempts;
    }

    public void setTotalQuizAttempts(int totalQuizAttempts) {
        this.totalQuizAttempts = totalQuizAttempts;
    }

    public ArrayList<LessonProgress> getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(ArrayList<LessonProgress> lessonProgress) {
        this.lessonProgress = lessonProgress;
    }
}
