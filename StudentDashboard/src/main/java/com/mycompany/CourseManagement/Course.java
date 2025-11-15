/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Zeyad
 */
public class Course {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1); // Start from 1

    public final int generateId() {
        return ID_GENERATOR.getAndIncrement();
    }
    private int courseId;
    private String instructorId;

    @JsonProperty(defaultValue = "[]")
    private ArrayList<String> studentIds;

    private String title;
    private String description;

    @JsonProperty(defaultValue = "[]")
    private ArrayList<Lesson> lessons;

    public Course() {
        this.studentIds = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public Course(String instructorId, String title, String description) {
        this.instructorId = instructorId;
        this.title = title;
        this.description = description;
        this.courseId = generateId();
        this.studentIds = new ArrayList<>();
        this.lessons = new ArrayList<>();
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
        return instructorId;
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

    public void addLesson(Lesson lesson) {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        if (lesson != null) {
            lessons.add(lesson);
        }
    }

    public ArrayList<Lesson> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        return lessons;
    }

    @Override
    public String toString() {
        return this.title + " " + this.description + " " + this.courseId;
    }

}
