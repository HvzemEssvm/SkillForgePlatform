/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Enrollment;
import com.mycompany.UserAccountManagement.Student;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class CourseServices {

    private static int foundAt;

    // Static initializer block to load data when class is first accessed
    static {
        JsonHandler.loadCourses();
    }

    // Course Management Methods
    /**
     * Reads course data from the 'courses.json' file and returns a list of
     * Course objects.
     *
     * @return a list of Course objects parsed from the JSON file
     * @throws IOException             if the file cannot be read
     * @throws JsonProcessingException if the JSON is invalid or cannot be
     *                                 mapped to Course
     */
    public static ArrayList<Course> getAllCourses() throws JsonProcessingException, IOException {
        return new ArrayList<>(JsonHandler.courses);
    }

    /**
     * Create a new course java object Then read the current list of courses in
     * the 'courses.json' and save it to courseList ArrayNode Then convert the
     * course java object to JSON node and add it to the courseList.
     *
     * @param instructorId
     * @param title
     * @param description
     * @return Course java object
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static Course createCourse(String instructorId, String title, String description)
            throws JsonProcessingException, IOException {

        Course course = new Course(instructorId, title, description);
        JsonHandler.courses.add(course);
        JsonHandler.saveCourses();

        return course;
    }

    /**
     * iterate over courseList ArrayNode and convert the node to course java
     * object and check if getCourseId equals the given id if equal put foundAt
     * to the index of course found in the ArrayNode and return the course
     * object.
     *
     * @param courseId
     * @return course java object
     * @throws IllegalArgumentException
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static Course findCourseById(String courseId)
            throws IllegalArgumentException, JsonProcessingException, IOException {
        for (int i = 0; i < JsonHandler.courses.size(); i++) {
            Course course = JsonHandler.courses.get(i);
            if (course.getCourseId().equals(courseId)) {
                foundAt = i;
                return course;
            }
        }
        return null;
    }

    /**
     * Helper method return the course object which have specific lesson Iterate
     * over the courseList ArrayNode and convert each Node to java object Then
     * get Lessons arrayList for each course and iterate over them to check if
     * the given id in the lessons ArrayList if found put foundAt variable to
     * the index of the course in the courseList and return the course java
     * object.
     *
     * @param lessonId
     * @return Course object which contain the specific lesson
     * @throws IOException
     */
    public static Course findCourseByLessonId(String lessonId) throws IOException {
        for (int i = 0; i < JsonHandler.courses.size(); i++) {
            Course course = JsonHandler.courses.get(i);

            ArrayList<Lesson> lessons = course.getLessons();
            if (lessons != null) {
                for (Lesson lesson : lessons) {
                    if (lesson.getLessonId().equals(lessonId)) {
                        foundAt = i;
                        return course;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param courseId
     * @param newDescription
     * @param newTitle
     * @return the updated course java object
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static Course updateCourse(String courseId, String newDescription, String newTitle)
            throws IllegalArgumentException, IOException {
        Course course = findCourseById(courseId);
        course.setDescription(newDescription);
        course.setTitle(newTitle);
        JsonHandler.saveCourses();
        return course;
    }

    public static boolean deleteCourseById(String courseId) throws IOException {
        for (int i = 0; i < JsonHandler.courses.size(); i++) {
            Course course = JsonHandler.courses.get(i);
            if (course.getCourseId().equals(courseId)) {
                // Get enrolled students BEFORE removing the course
                ArrayList<String> enrolledStudents = course.getStudentIds();

                // Remove course from courses list
                JsonHandler.courses.remove(i);

                // Remove enrollment from all enrolled students
                if (enrolledStudents != null) {
                    for (String studentId : enrolledStudents) {
                        Student student = JsonHandler.getStudent(studentId);
                        if (student != null) {
                            student.getEnrollments()
                                    .removeIf(enrollment -> enrollment.getCourseId().equals(courseId));
                        }
                    }
                }

                // Save both files
                JsonHandler.saveCourses();
                JsonHandler.saveUsers();
                return true;
            }
        }

        return false;
    }

    public static Lesson addLessonToCourse(String courseId, Lesson lesson)
            throws IllegalArgumentException, JsonProcessingException, IOException {
        Course course = findCourseById(courseId);
        course.addLesson(lesson);
        JsonHandler.saveCourses();
        return lesson;
    }

    // ------------------------
    // Enroll course and return enrolled courses by a student
    // ------------------------
    // Enroll student in a course
    public static boolean enrollStudentInCourse(String courseId, String studentId)
            throws IllegalArgumentException, JsonProcessingException, IOException {
        Student student = JsonHandler.getStudent(studentId);

        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }

        ArrayList<String> studentIds = course.getStudentIds();
        if (studentIds.contains(studentId)) {
            return false; // Already enrolled
        }

        boolean enrolled = course.enrollStudent(studentId);

        if (enrolled) {
            Enrollment enrollment = new Enrollment(courseId, new ArrayList<>());
            student.getEnrollments().add(enrollment);

            JsonHandler.saveCourses();
            JsonHandler.saveUsers();
        }

        return enrolled;
    }

    // return list of enrolled courses by specific student
    public static ArrayList<Course> getEnrolledCoursesByStudent(String studentId)
            throws JsonProcessingException, IOException {

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }

        ArrayList<Course> enrolledCourses = new ArrayList<>();

        for (Course course : JsonHandler.courses) {
            ArrayList<String> studentIds = course.getStudentIds();
            if (studentIds != null && studentIds.contains(studentId)) {
                enrolledCourses.add(course);
            }
        }

        return enrolledCourses;
    }

    // return list of students id who enrolled in specific course
    public static ArrayList<String> getEnrolledStudents(String courseId)
            throws IllegalArgumentException, JsonProcessingException, IOException {

        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }

        return course.getStudentIds();
    }

    // --------------------------------------------------------
    // Lesson Management Methods
    // --------------------------------------------------------
    public static ArrayList<Lesson> getAllLessonsFromCourse(String courseId)
            throws IllegalArgumentException, JsonProcessingException, IOException {

        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }

        return course.getLessons();
    }

    public static Lesson createLesson(String title, String content) {
        return new Lesson(title, content);
    }

    // Use this if you know already the course id which have the lesson
    public static Lesson findLessonById(String courseId, String lessonId)
            throws IllegalArgumentException, JsonProcessingException, IOException {

        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }

        ArrayList<Lesson> lessons = course.getLessons();
        if (lessons == null) {
            return null;
        }

        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }

        return null;
    }

    // Use this if you don't know the course id which have the lesson
    public static Lesson findLessonById(String lessonId)
            throws JsonProcessingException, IOException {

        for (int i = 0; i < JsonHandler.courses.size(); i++) {
            Course course = JsonHandler.courses.get(i);

            ArrayList<Lesson> lessons = course.getLessons();
            if (lessons != null) {
                for (Lesson lesson : lessons) {
                    if (lesson.getLessonId().equals(lessonId)) {
                        foundAt = i; // Store index for later use
                        return lesson;
                    }
                }
            }
        }

        return null;
    }

    public static Lesson updateLessonById(String lessonId, String newTitle, String newContent)
            throws IllegalArgumentException, JsonProcessingException, IOException {

        Lesson lesson = findLessonById(lessonId);

        if (lesson == null) {
            throw new IllegalArgumentException("Lesson with ID " + lessonId + " not found");
        }

        lesson.setTitle(newTitle);
        lesson.setContent(newContent);
        JsonHandler.saveCourses();

        return lesson;
    }

    public static boolean deleteLessonById(String lessonId)
            throws JsonProcessingException, IOException {

        Lesson lesson = findLessonById(lessonId);

        if (lesson == null) {
            return false;
        }

        Course course = JsonHandler.courses.get(getIndex());
        ArrayList<String> studentIds = course.getStudentIds();
        ArrayList<Lesson> lessons = course.getLessons();
        boolean removed = false;

        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(lessonId)) {
                lessons.remove(i);
                removed = true;
                break;
            }
        }

        for (String studentId : studentIds) {
            Student student = JsonHandler.getStudent(studentId);
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getCourseId().equals(course.getCourseId())) {
                    enrollment.getCompletedLessons().remove(lessonId);
                }
            }
        }

        if (removed) {
            JsonHandler.saveCourses();
            JsonHandler.saveUsers();
        }

        return removed;
    }

    // Check if lesson is completed
    public static boolean isLessonCompleted(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);

        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseId().equals(courseId)) {
                return enrollment.getCompletedLessons().contains(lessonId);
            }
        }
        return false;
    }

    public static void markLessonCompleted(String studentId, String lessonId) {
        String courseId;
        Student student = JsonHandler.getStudent(studentId);
        try {
            Course course = findCourseByLessonId(lessonId);
            courseId = course.getCourseId();
        } catch (IOException ex) {
            courseId = null;
        }

        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseId().equals(courseId)) {
                if (!enrollment.getCompletedLessons().contains(lessonId)) {
                    enrollment.getCompletedLessons().add(lessonId);
                    JsonHandler.saveUsers();
                }
                return;
            }
        }
    }

    /**
     * @return the index
     */
    public static int getIndex() {
        return foundAt;
    }

    public static ArrayList<Course> getCoursesByStatus(Status status) throws IOException,JsonProcessingException
    {
        ArrayList<Course> courses = new ArrayList<>();
        for(Course  course   :   CourseServices.getAllCourses())
        {
            if(course.getStatus()==status)
            {
                courses.add(course);
            }
        }
        return courses;
    }
    
    public static Course updateCourseStatus(String courseId,Status status) throws IllegalArgumentException, IOException
    {
        Course course = CourseServices.findCourseById(courseId);
        course.setStatus(status);
        JsonHandler.saveCourses();
        return course;
    }
    
}
