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
    private ArrayNode courseList;
    private int index;

    public CourseServices() throws IOException {
        this.fileName = "courses.json";
        // Initialize file if it doesn't exist or is empty
        JsonHandler.initializeFileIfNeeded(fileName);
        courseList = JsonHandler.readArrayFromFile(fileName);
    }
    
    public Course createCourse(String instructorId, String title, String description)
            throws JsonProcessingException, IOException {
        
        Course course = new Course(instructorId, title, description);
        JsonNode node = JsonHandler.convertJavatoJson(course);
        
        courseList = JsonHandler.readArrayFromFile(fileName);
        courseList.add(node);
        JsonHandler.writeToFile(courseList, fileName);
        
        return course;
    }
    
    public Course findById(int id) throws IllegalArgumentException, JsonProcessingException, IOException {
        courseList = JsonHandler.readArrayFromFile(fileName);
        for (int i = 0; i < courseList.size(); i++) {
            JsonNode node = courseList.get(i);
            if (node.get("courseId").asText().equals(String.valueOf(id))) {
                index = i;
                return JsonHandler.objectMapper.treeToValue(node, Course.class);
            }
        }
        return null;
    }
    
    public Course updateCourse(int id, String newDescription, String newTitle) throws IllegalArgumentException, IOException {
        Course course = findById(id);
        course.setDescription(newDescription);
        course.setTitle(newTitle);
        JsonNode jsonNode = JsonHandler.convertJavatoJson(course);
        courseList.set(index, jsonNode);
        JsonHandler.writeToFile(courseList, fileName);
        return course;
    }
    
    public boolean deleteCourseById(int id) throws IOException {
        ArrayNode courseList = JsonHandler.readArrayFromFile(fileName);
        
        for (int i = 0; i < courseList.size(); i++) {
            JsonNode node = courseList.get(i);
            if (node.get("courseId").asText().equals(String.valueOf(id))) {
                courseList.remove(i);
                JsonHandler.writeToFile(courseList, fileName);
                return true;
            }
        }
        
        return false;
    }
}
