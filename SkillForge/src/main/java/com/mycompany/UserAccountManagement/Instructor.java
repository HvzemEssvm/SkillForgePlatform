/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * 
 */
public class Instructor extends User {

    public Instructor() {
        super();
    }

    public Instructor(String userId, String name, String email, String password) throws IOException {
        super(userId, name, email, password);
    }

    public void createCourse(String title, String description) throws Exception {
        CourseServices.createCourse(getUserId(), title, description);
        UserServices.updateUser(this);
    }

    public Lesson createLesson(String title, String content) throws IOException {
        Lesson lesson = CourseServices.createLesson(title, content);
        try {
            UserServices.updateUser(this);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return lesson;
    }

    public void uploadLesson(Lesson lesson, String courseId) throws IllegalArgumentException, IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to add lessons to this course!");
        }

        CourseServices.addLessonToCourse(courseId, lesson);

        try {
            UserServices.updateUser(this);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void updateCourse(String courseId, String newTitle, String newDescription) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to update this course!");
        }

        CourseServices.updateCourse(courseId, newDescription, newTitle);

        try {
            UserServices.updateUser(this);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void deleteCourse(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to delete this course!");
        }

        CourseServices.deleteCourseById(courseId);

        try {
            UserServices.updateUser(this);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void updateLesson(String lessonId, String newTitle, String newContent) throws IOException {
        Course course = CourseServices.findCourseByLessonId(lessonId);

        if (course == null) {
            throw new IllegalArgumentException("Lesson not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to update this lesson!");
        }

        CourseServices.updateLessonById(lessonId, newTitle, newContent);

        try {
            UserServices.updateUser(this);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void deleteLesson(String lessonId) throws IOException {
        Course course = CourseServices.findCourseByLessonId(lessonId);

        if (course == null) {
            throw new IllegalArgumentException("Lesson not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to delete this lesson!");
        }

        CourseServices.deleteLessonById(lessonId);
    }

    @JsonIgnore
    public ArrayList<Course> getMyCourses() throws IOException {
        ArrayList<Course> allCourses = CourseServices.getAllCourses();
        ArrayList<Course> myCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getInstructorId().equals(getUserId())) {
                myCourses.add(course);
            }
        }
        return myCourses;
    }

    public ArrayList<String> getMyCoursesIds() throws IOException {
        ArrayList<Course> allCourses = CourseServices.getAllCourses();
        ArrayList<String> myCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getInstructorId().equals(getUserId())) {
                myCourses.add(course.getCourseId());
            }
        }
        return myCourses;
    }

    @JsonIgnore
    public ArrayList<String> getEnrolledStudents(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to view this course's students!");
        }

        return CourseServices.getEnrolledStudents(courseId);
    }

    @JsonIgnore
    public ArrayList<Lesson> getCourseLessons(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        if (!course.getInstructorId().equals(getUserId())) {
            throw new IllegalArgumentException("You don't have permission to view this course's lessons!");
        }

        return CourseServices.getAllLessonsFromCourse(courseId);
    }
}