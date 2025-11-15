/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Zeyad
 */
public class Lesson {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1); // Start from 1

    private int lessonId;
    private String title;
    private String content;

    public Lesson() {
    }

    public Lesson(String title, String content) {
        this.lessonId = generateId();
        this.title = title;
        this.content = content;
    }

    /**
     * @return the lessonId
     */
    public final int generateId() {
        return ID_GENERATOR.getAndIncrement();
    }

    public int getLessonId() {
        return lessonId;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Lesson[ID=%d, Title='%s', Content='%s']",
                lessonId, title, content);
    }

}
