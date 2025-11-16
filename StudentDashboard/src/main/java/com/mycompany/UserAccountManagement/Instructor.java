/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Hazem
 */
public class Instructor extends User {

    private final CourseServices courseManager;

    public Instructor(String userId, String name, String email, String password) throws IOException {
        super(userId, name, email, password);
        courseManager = new CourseServices();
    }

    // Add business rules specific to instructors
    public Course createCourse(String title, String description) throws IOException {
        return courseManager.createCourse(getUserId(), title, description);
    }

    public void uploadLesson(Lesson lesson, String courseId) throws IOException {
        // Authorization: Check if this instructor owns the course
        Course course = courseManager.findCourseById(courseId);
        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't own this course!");
        }
        courseManager.addLessonToCourse(courseId, lesson);
    }

    public Lesson createLesson(String title, String content) throws IOException {
        return courseManager.createLesson(title, content);
    }

    public void updateLesson(String lessonId, String newTitle, String newContent) throws IOException {
        Lesson lesson = courseManager.findLessonById(lessonId);

        if (lesson == null) {
            throw new IllegalArgumentException("Lesson not found");
        }

        ArrayList<Course> allCourses = courseManager.getAllCourses();
        Course course = allCourses.get(courseManager.getIndex());

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to update this lesson!");
        }

        courseManager.updateLessonById(lessonId, newTitle, newContent);
    }

    public void deleteLesson(String lessonId) throws IOException {
        // Similar authorization check
        courseManager.deleteLessonById(lessonId);
    }

    // Get only courses created by THIS instructor
    public ArrayList<Course> getMyCourses() throws IOException {
        ArrayList<Course> allCourses = courseManager.getAllCourses();
        ArrayList<Course> myCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getInstructorId().equals(getUserId())) {
                myCourses.add(course);
            }
        }
        return myCourses;
    }
}
