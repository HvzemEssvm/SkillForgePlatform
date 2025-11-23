/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

/**
 *
 * @author HP
 */
public class QuizAttempt {
    private String quizId;
    private String lessonId;
    private String courseId;
    private String studentId;
    private int scorePercent;
    private boolean passed;
    private String attemptTime; //Instant().now().tostring()

    
    public QuizAttempt() {}

    
    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) 
    {
        this.lessonId = lessonId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId)
    { 
        this.courseId = courseId; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getScorePercent() { return scorePercent; }
    public void setScorePercent(int scorePercent) 
    { 
        this.scorePercent = scorePercent; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) 
    { 
        this.passed = passed; }

    public String getAttemptTime() { return attemptTime; }
    public void setAttemptTime(String attemptTime) 
    { 
        this.attemptTime = attemptTime; }
    
}
