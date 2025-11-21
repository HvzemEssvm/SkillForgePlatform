/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.CourseManagement.Status;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 *
 */
public class Student extends User {

    private ArrayList<Enrollment> enrollments;

    public Student() {
        super();
        this.enrollments = new ArrayList<>();
    }

    public Student(String userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.enrollments = new ArrayList<>();
    }

    public void enroll(String courseId) throws IllegalArgumentException, IOException {
        CourseServices.enrollStudentInCourse(courseId, getUserId());
    }

    public void completeLesson(String userId, String lessonId) throws IOException {
        CourseServices.markLessonCompleted(userId, lessonId);
    }

    @JsonIgnore
    public ArrayList<Course> viewAvailableCourses() throws IOException {
        ArrayList<Course> allCourses = CourseServices.getCoursesByStatus(Status.APPROVED);
        ArrayList<Course> enrolledCourses = getMyEnrolledCourses();
        allCourses.removeAll(enrolledCourses);
        
        return allCourses;
    }

    @JsonIgnore
    public ArrayList<Course> getMyEnrolledCourses() throws IOException {
        return CourseServices.getEnrolledCoursesByStudent(getUserId());
    }

    @JsonIgnore
    public ArrayList<Lesson> getCourseLessons(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        if (course == null||course.getStatus()!=Status.APPROVED) {
            throw new IllegalArgumentException("Course not found");
        }

        return CourseServices.getAllLessonsFromCourse(courseId);
    }

    /**
     * @return the enrolledCourses
     */
    public ArrayList<Enrollment> getEnrollments() {
        if (enrollments == null) {
            enrollments = new ArrayList<>();
        }
        return enrollments;
    }
}
