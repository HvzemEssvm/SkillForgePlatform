/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Student;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

/**
 *
 * @author HP
 */


public class ProgressAndCertificateManager {

  
    public static void addQuizAttempt(String studentId, QuizAttempt attempt) throws IOException {
        StudentProgress sp = loadStudentProgress(studentId);
        sp.addQuizAttempt(attempt);
    }

    
    public static void markLessonCompleted(String studentId, String courseId, String lessonId) throws IOException {
        StudentProgress sp = loadStudentProgress(studentId);
        sp.markLessonCompleted(courseId, lessonId);
    }

    
    public static Certificate generateCertificate(Student student, String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        String certId = UUID.randomUUID().toString();
        String issueDate = Instant.now().toString();
        String filePath = "certificates/" + certId + ".json";

        Certificate cert = new Certificate(
                certId,
                course.getCourseId(),
                course.getTitle(),
                student.getUserId(),
                student.getName(),
                issueDate,
                filePath
        );

        
        File dir = new File("certificates");
        if (!dir.exists()) dir.mkdirs();
        JsonHandler.writeToFile(JsonHandler.convertJavatoJson(cert), filePath);

        return cert;
    }

    
    public static StudentProgress loadStudentProgress(String studentId) throws IOException {
        String path = "studentProgress_" + studentId + ".json";
        File file = new File(path);

        StudentProgress sp;
        if (file.exists() && file.length() > 0) {
            sp = (StudentProgress) JsonHandler.objectMapper.readValue(file, StudentProgress.class);
        } else {
            Student student = JsonHandler.getStudent(studentId);
            sp = new StudentProgress(studentId, student.getName());
        }
        return sp;
    }

    
    public static void saveStudentProgress(StudentProgress sp) throws IOException {
        String path = "studentProgress_" + sp.getStudentId() + ".json";
        JsonHandler.writeToFile(JsonHandler.convertJavatoJson(sp), path);
    }
}

        
        
