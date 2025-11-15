/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mycompany.JsonHandler.JsonHandler;
import java.io.IOException;

/**
 *
 * @author Zeyad
 */
public class CourseServices {

    private final String fileName;

    public CourseServices() throws IOException {
        this.fileName = "courses.json";
        // Initialize file if it doesn't exist or is empty
        JsonHandler.initializeFileIfNeeded(fileName);
    }

    public Course createCourse(String instructorId, String title, String description)
            throws JsonProcessingException, IOException {

        Course course = new Course(instructorId, title, description);
        JsonNode node = JsonHandler.convertJavatoJson(course);

        ArrayNode courseList = JsonHandler.readArrayFromFile(fileName);
        courseList.add(node);
        JsonHandler.writeToFile(courseList, fileName);

        return course;
    }
}
