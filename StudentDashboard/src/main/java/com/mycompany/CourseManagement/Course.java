/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Mega
 */
public class Course {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1); // Start from 1

    public final int generateId() {
        return ID_GENERATOR.getAndIncrement();
    }
    private final int courseId;
    private final String InstructorId;
    private ArrayList<String> studentIds;
    private String title;
    private String description;
    private ArrayList<Lesson> lessons;


    public Course(String InstructorId, String title, String description) {
        this.InstructorId = InstructorId;
        this.title = title;
        this.description = description;
        this.courseId = generateId();
    }

    
    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @return the InstructorId
     */
    public String getInstructorId() {
        return InstructorId;
    }

    /**
     * @return the studentIds
     */
    public ArrayList<String> getStudentIds() {
        return studentIds;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lessons
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

}
