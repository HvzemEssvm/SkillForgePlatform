/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentdashboard;

import java.util.ArrayList;

import java.util.*;


/**
 *
 * @author afifi.store
 */
public class CourseManager {
    
  ArrayList<Course> availableCourses = new ArrayList<>();
  ArrayList<Course> enrolledCourses = new ArrayList<>();

    public CourseManager() {
        availableCourses.add(new Course("c1", "Intro to Java"));
        availableCourses.add(new Course("c2", "Data Structures"));
    }

    public ArrayList<Course> loadAvailableCourses() {
        return availableCourses;
    }
    public void enrollInCourse(String courseId) {
    for (Course c : availableCourses) {
        if (c.id.equals(courseId)) {
            enrolledCourses.add(c);
            System.out.println("Enrolled in: " + c.title);
            return;
        }
    }
    System.out.println("Course not found!");
}
    
}
