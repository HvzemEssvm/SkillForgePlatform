package com.mycompany.Analytics;

import java.util.ArrayList;

/**
 * Analytics data structure for course-level insights
 */
public class CourseAnalytics {
    private String courseId;
    private String courseTitle;
    private int totalStudents;
    private int activeStudents; // Students who completed at least 1 lesson
    private double averageCompletionPercentage;
    private double averageScore;
    private ArrayList<LessonAnalytics> lessonAnalytics;
    private ArrayList<StudentPerformance> studentPerformances;

    public CourseAnalytics() {
        this.lessonAnalytics = new ArrayList<>();
        this.studentPerformances = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(int activeStudents) {
        this.activeStudents = activeStudents;
    }

    public double getAverageCompletionPercentage() {
        return averageCompletionPercentage;
    }

    public void setAverageCompletionPercentage(double averageCompletionPercentage) {
        this.averageCompletionPercentage = averageCompletionPercentage;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public ArrayList<LessonAnalytics> getLessonAnalytics() {
        return lessonAnalytics;
    }

    public void setLessonAnalytics(ArrayList<LessonAnalytics> lessonAnalytics) {
        this.lessonAnalytics = lessonAnalytics;
    }

    public ArrayList<StudentPerformance> getStudentPerformances() {
        return studentPerformances;
    }

    public void setStudentPerformances(ArrayList<StudentPerformance> studentPerformances) {
        this.studentPerformances = studentPerformances;
    }
}