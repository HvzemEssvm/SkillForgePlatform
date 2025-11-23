/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.JsonHandler.JsonHandler;
import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    private String quizId; // إضافة هذا الحقل الجديد

    @JsonIgnore
    private boolean completed;

    public Lesson() {
    }

    public Lesson(String title, String content) {
        this.lessonId = "L" + generateNextLessonId();
        this.title = title;
        this.content = content;
        this.completed = false;
        this.quizId = null; // قيمة ابتدائية
    }

    private int generateNextLessonId() {
        int maxId = 0;

        if (JsonHandler.courses != null) {
            for (Course course : JsonHandler.courses) {
                ArrayList<Lesson> lessons = course.getLessons();
                if (lessons != null) {
                    for (Lesson lesson : lessons) {
                        if (lesson.getLessonId() != null && lesson.getLessonId().length() > 1) {
                            String id = lesson.getLessonId().substring(1);
                            int lessonNum = Integer.parseInt(id);
                            if (lessonNum > maxId) {
                                maxId = lessonNum;
                            }
                        }
                    }
                }
            }
        }

        return maxId + 1;
    }

    /**
     * @return the lessonId
     */
    public String getLessonId() {
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

    /**
     * @return the quizId
     */
    public String getQuizId() {
        return quizId;
    }

    /**
     * @param quizId the quizId to set
     */
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return String.format("Lesson[ID=%s, Title='%s', Content='%s']",
                lessonId, title, content);
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}