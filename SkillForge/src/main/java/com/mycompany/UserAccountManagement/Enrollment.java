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

    public Enrollment(String courseId, ArrayList<String> completedLessons) {
        this.courseId = courseId;
        this.completedLessons = completedLessons;
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
