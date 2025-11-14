/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentdashboard;

/**
 *
 * @author afifi.store
 */
public class StudentDashboard {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        
        CourseManager cm = new CourseManager();

        System.out.println("Available courses:");
        for (Course c : cm.loadAvailableCourses()) {
            System.out.println(c.id + " — " + c.title);
        }
        
       

        System.out.println("Available courses:");
        for (Course c : cm.loadAvailableCourses()) {
            System.out.println(c.id + " — " + c.title);
        }

        System.out.println("\nTrying to enroll in c1...");
        cm.enrollInCourse("c1");
    }
        
        
    }

