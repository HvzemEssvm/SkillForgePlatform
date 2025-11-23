/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author menna
 */


public class Certificate {
    private String certificateId;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseTitle;
    private Date issueDate;
    private String instructorName;
    private double finalScore;

    public Certificate() {
    }

    public Certificate(String studentId, String studentName, String courseId, 
                      String courseTitle, String instructorName, double finalScore) {
        this.certificateId = "CERT_" + System.currentTimeMillis() + "_" + 
                           UUID.randomUUID().toString().substring(0, 8);
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
        this.finalScore = finalScore;
        this.issueDate = new Date();
    }

    
    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(issueDate);
    }

    @Override
    public String toString() {
        return String.format("Certificate[ID=%s, Student=%s, Course=%s, Score=%.1f%%, Date=%s]",
                certificateId, studentName, courseTitle, finalScore, getFormattedDate());
    }
}
/**
 *
 * @author HP
 */
public class Certificate {
    private String certId;
    private String courseId;
    private String courseTitle;
    private String studentId;
    private String studentName;
    private String issueDate;
    private String filePath; // path to the JSON file

    public Certificate() {}

    public Certificate(String certId, String courseId, String courseTitle,
                       String studentId, String studentName, String issueDate,
                       String filePath) {
        this.certId = certId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.studentId = studentId;
        this.studentName = studentName;
        this.issueDate = issueDate;
        this.filePath = filePath;
    }

    
    public String getCertId() { 
        return certId; }
    public void setCertId(String certId) {
        this.certId = certId; }

    public String getCourseId()
    { 
        return courseId; }
    public void setCourseId(String courseId) 
    { 
        this.courseId = courseId; }

    public String getCourseTitle() 
    { 
        return courseTitle; }
    public void setCourseTitle(String courseTitle) 
    { 
        this.courseTitle = courseTitle; }

    public String getStudentId() 
    { 
        return studentId; }
    public void setStudentId(String studentId) 
    { 
        this.studentId = studentId; }

    public String getStudentName() 
    { 
        return studentName; }
    public void setStudentName(String studentName) 
    { 
        this.studentName = studentName; }

    public String getIssueDate() 
    { 
        return issueDate; }
    public void setIssueDate(String issueDate) 
    { 
        this.issueDate = issueDate; }

    public String getFilePath() 
    { 
        return filePath; }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath; }
}
