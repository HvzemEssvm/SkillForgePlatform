/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Enrollment;
import java.io.IOException;
import com.mycompany.CourseManagement.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */


public class StudentProgress {

    private String studentId;
    private String name;

    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private ArrayList<Certificate> certificates = new ArrayList<>();

    // Map courseId -> CourseProgress
    private Map<String, CourseProgress> courseProgress = new HashMap<>();

    public StudentProgress() {}

    public StudentProgress(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(ArrayList<Enrollment> enrollments) { this.enrollments = enrollments; }

    public ArrayList<Certificate> getCertificates() { return certificates; }
    public void setCertificates(ArrayList<Certificate> certificates) { this.certificates = certificates; }

    public Map<String, CourseProgress> getCourseProgress() { return courseProgress; }
    public void setCourseProgress(Map<String, CourseProgress> courseProgress) { this.courseProgress = courseProgress; }

   

    public CourseProgress getOrCreateCourseProgress(String courseId) {
        if (courseProgress == null) courseProgress = new HashMap<>();
        CourseProgress cp = courseProgress.get(courseId);
        if (cp == null) {
            cp = new CourseProgress();
            courseProgress.put(courseId, cp);
        }
        return cp;
    }

    // Mark lesson completed updates course progress
    public void markLessonCompleted(String courseId, String lessonId) throws IOException {
        CourseProgress cp = getOrCreateCourseProgress(courseId);
        cp.updateLessonStatus(lessonId, true);

        // If course completed and no certificate yet
        if (cp.getCompleted() && cp.getCertificateId() == null) {
            Certificate cert = (Certificate) ProgressAndCertificateManager.generateCertificate(
                    JsonHandler.getStudent(studentId),
                    courseId
            );
            cp.setCertificateId(cert.getCertId());
            certificates.add(cert);
        }

        
        ProgressAndCertificateManager.saveStudentProgress(this);
    }

    
    public void addQuizAttempt(QuizAttempt attempt) throws IOException {
        CourseProgress cp = getOrCreateCourseProgress(attempt.getCourseId());
        cp.addQuizAttempt(attempt);

        
        if (cp.getCompleted() && cp.getCertificateId() == null) {
            Certificate cert = (Certificate) ProgressAndCertificateManager.generateCertificate(
                    JsonHandler.getStudent(studentId),
                    attempt.getCourseId()
            );
            cp.setCertificateId(cert.getCertId());
            certificates.add(cert);
        }

        
        ProgressAndCertificateManager.saveStudentProgress(this);
    }
}
