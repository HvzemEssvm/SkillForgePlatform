/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.mycompany.UserAccountManagement.Enrollment;
import java.security.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */
public class Student {
    private String studentId;
    private String name;

    // existing fields
    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private ArrayList<Certificate> certificates = new ArrayList<>();

    // map courseId -> CourseProgress
    private Map<String, CourseProgress> courseProgress = new HashMap<>();

    public Student() {}

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    
    public String getStudentId() {
        return studentId; 
    }
    public void setStudentId(String studentId) { 
        this.studentId = studentId; 
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(ArrayList<Enrollment> enrollments) { this.enrollments = enrollments; }

    public ArrayList<Certificate> getCertificates() { return certificates; }
    public void setCertificates(ArrayList<Certificate> certificates) { this.certificates = certificates; }

    
    public Map<String, CourseProgress> getCourseProgress() {
        return courseProgress; }
    public void setCourseProgress(Map<String, CourseProgress> courseProgress) 
    { 
        this.courseProgress = courseProgress; }

   
    public CourseProgress getOrCreateCourseProgress(String courseId) {
        if (courseProgress == null) courseProgress = new HashMap<>();
        CourseProgress cp = courseProgress.get(courseId);
        if (cp == null) {
            cp = new CourseProgress();
            courseProgress.put(courseId, cp);
        }
        return cp;
    }
    
}
