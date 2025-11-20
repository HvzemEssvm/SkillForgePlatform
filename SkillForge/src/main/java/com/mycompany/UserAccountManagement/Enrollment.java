/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class Enrollment {

    private String courseId;
    private ArrayList<String> completedLessons;

    // Default constructor for Jackson deserialization
    public Enrollment() {
        this.completedLessons = new ArrayList<>();
    }

    public Enrollment(String courseId, ArrayList<String> completedLessons) {
        this.courseId = courseId;
        this.completedLessons = completedLessons != null ? completedLessons : new ArrayList<>();
    }

    /**
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @return the completedLessons
     */
    public ArrayList<String> getCompletedLessons() {
        return completedLessons;
    }
}
