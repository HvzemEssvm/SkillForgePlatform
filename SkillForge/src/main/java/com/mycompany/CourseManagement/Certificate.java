/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

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
