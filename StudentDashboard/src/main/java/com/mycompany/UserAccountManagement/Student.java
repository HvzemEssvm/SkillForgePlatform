/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.JsonHandler.JsonHandler;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Hazem
 */
public class Student extends User {
    private final CourseServices courseManager;
    
    public Student(String userId, String name, String email, String password) throws IOException {
        super(userId, name, email, password);
        courseManager = new CourseServices();
    }
    
    public void enroll(String courseId) throws IOException {
        courseManager.enrollStudentInCourse(courseId, getUserId());
    }
    
    public void completeLesson(String lessonId) throws IOException {
        // Check if student is enrolled in the course
        ArrayNode courseList = JsonHandler.readArrayFromFile(courseManager.getFileName());
        courseManager.findLessonById(lessonId);
        
        JsonNode node = courseList.get(courseManager.getIndex());
        Course course = JsonHandler.objectMapper.treeToValue(node, Course.class);
        
        // Check enrollment
        if (!course.getStudentIds().contains(getUserId())) {
            throw new IllegalArgumentException("You must enroll in this course first!");
        }
        
        // Update lesson
        ArrayList<Lesson> lessons = course.getLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                lesson.setCompleted(true);
                break;
            }
        }
        
        JsonNode jsonNode = JsonHandler.convertJavatoJson(course);
        courseList.set(courseManager.getIndex(), jsonNode);
        JsonHandler.writeToFile(courseList, courseManager.getFileName());
    }
    
    // Get only courses this student is enrolled in
    public ArrayList<Course> getMyEnrolledCourses() throws IOException {
        return courseManager.getEnrolledCoursesByStudent(getUserId());
    }
    
    // View lessons in enrolled courses only
    public ArrayList<Lesson> getCourseLessons(String courseId) throws IOException {
        Course course = courseManager.findCourseById(courseId);
        
        // Check if enrolled
        if (!course.getStudentIds().contains(getUserId())) {
            throw new IllegalArgumentException("You are not enrolled in this course!");
        }
        
        return courseManager.getAllLessonsFromCourse(courseId);
    }

}
