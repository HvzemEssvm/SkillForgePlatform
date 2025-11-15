/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

/**
 *
 * @author Mega
 */
public class Lesson {

    private String lessonId;
    private String title;
    private String content;

    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
    }
    
    

    /**
     * @return the lessonId
     */
    public String getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId the lessonId to set
     */
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
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

}
