/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.JsonHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.CourseManagement.Course;
import com.mycompany.UserAccountManagement.Instructor;
import com.mycompany.UserAccountManagement.Student;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class JsonHandler {

    public static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static ArrayList<Course> courses;
    public static ArrayList<Student> students;
    public static ArrayList<Instructor> instructors;

    public static Student getStudent(String studentId) {
        for (Student student : students) {
            if (student.getUserId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public static void saveUsers() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("users.json"),
                            new UsersWrapper(students, instructors));
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveCourses() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("courses.json"),
                            new CoursesWrapper(courses));
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        try {
            File usersFile = new File("users.json");
            if (!usersFile.exists() || usersFile.length() == 0) {
                System.out.println("Users file not found or empty, creating new lists");
                students = new ArrayList<>();
                instructors = new ArrayList<>();
                return;
            }

            // Load users.json
            UsersWrapper uw = objectMapper.readValue(usersFile, UsersWrapper.class);
            students = uw.students != null ? uw.students : new ArrayList<>();
            instructors = uw.instructors != null ? uw.instructors : new ArrayList<>();
            // System.out.println("Loaded " + students.size() + " students and " +
            // instructors.size() + " instructors");
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
            students = new ArrayList<>();
            instructors = new ArrayList<>();
        }
    }

    public static void loadCourses() {
        try {
            File coursesFile = new File("courses.json");
            if (!coursesFile.exists() || coursesFile.length() == 0) {
                System.out.println("Courses file not found or empty, creating new list");
                courses = new ArrayList<>();
                return;
            }

            // Load courses.json
            CoursesWrapper cw = objectMapper.readValue(coursesFile, CoursesWrapper.class);
            courses = cw.courses != null ? cw.courses : new ArrayList<>();
            // System.out.println("Loaded " + courses.size() + " courses");
        } catch (IOException e) {
            System.err.println("Error loading courses: " + e.getMessage());
            e.printStackTrace();
            courses = new ArrayList<>();
        }
    }

    public static void writeToFile(JsonNode node, String fileName) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(fileName), node);
    }

    public static JsonNode readFromFile(String fileName) throws IOException {
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            return null;
        }

        return objectMapper.readTree(file);
    }

    public static ArrayNode readArrayFromFile(String fileName) throws IOException {
        JsonNode jsonNode = readFromFile(fileName);

        // If file was empty or doesn't exist, return empty array
        if (jsonNode == null) {
            return objectMapper.createArrayNode();
        }

        // Verify it's an array
        if (jsonNode.isArray()) {
            return (ArrayNode) jsonNode;
        } else {
            throw new IllegalStateException(
                    "File '" + fileName + "' does not contain a JSON array");
        }
    }

    // Initialize file with empty array if it doesn't exist or is empty
    public static void initializeFileIfNeeded(String fileName) throws IOException {
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            ArrayNode emptyArray = objectMapper.createArrayNode();
            writeToFile(emptyArray, fileName);
        }
    }

    public static JsonNode convertJavatoJson(Object obj) throws JsonProcessingException {
        return objectMapper.valueToTree(obj);
    }
}
