package com.mycompany.CourseManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Enrollment;
import com.mycompany.UserAccountManagement.Student;
import java.io.IOException;
import java.util.ArrayList;

public class CourseServices {
    private static int foundAt;

    static {
        JsonHandler.loadCourses();
    }

    public static ArrayList<Course> getAllCourses() throws JsonProcessingException, IOException {
        return new ArrayList<>(JsonHandler.courses);
    }

    public static Course createCourse(String instructorId, String title, String description)
            throws JsonProcessingException, IOException {
        Course course = new Course(instructorId, title, description);
        JsonHandler.courses.add(course);
        JsonHandler.saveCourses();
        return course;
    }

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
                ArrayList<String> enrolledStudents = course.getStudentIds();
                JsonHandler.courses.remove(i);

                if (enrolledStudents != null) {
                    for (String studentId : enrolledStudents) {
                        Student student = JsonHandler.getStudent(studentId);
                        if (student != null) {
                            student.getEnrollments()
                                    .removeIf(enrollment -> enrollment.getCourseId().equals(courseId));
                        }
                    }
                }

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
            return false;
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

    public static ArrayList<String> getEnrolledStudents(String courseId)
            throws IllegalArgumentException, JsonProcessingException, IOException {
        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }
        return course.getStudentIds();
    }

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

    public static Lesson findLessonById(String lessonId)
            throws JsonProcessingException, IOException {
        for (int i = 0; i < JsonHandler.courses.size(); i++) {
            Course course = JsonHandler.courses.get(i);
            ArrayList<Lesson> lessons = course.getLessons();
            if (lessons != null) {
                for (Lesson lesson : lessons) {
                    if (lesson.getLessonId().equals(lessonId)) {
                        foundAt = i;
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

    public static int getIndex() {
        return foundAt;
    }

    public static ArrayList<Course> getCoursesByStatus(Status status) throws IOException,JsonProcessingException {
        ArrayList<Course> courses = new ArrayList<>();
        for(Course course : CourseServices.getAllCourses()) {
            if(course.getStatus()==status) {
                courses.add(course);
            }
        }
        return courses;
    }
    
    public static Course updateCourseStatus(String courseId,Status status) throws IllegalArgumentException, IOException {
        Course course = CourseServices.findCourseById(courseId);
        course.setStatus(status);
        JsonHandler.saveCourses();
        return course;
    }
    
    public static Course updateCourseStatus(String courseId,String status) throws IllegalArgumentException, IOException {
        Course course = CourseServices.findCourseById(courseId);
        course.setStatus(Status.valueOf(status));
        JsonHandler.saveCourses();
        return course;
    }

    // الدالة الجديدة - تحقق إذا الطالب نجح في أي كويز
    public static boolean hasStudentPassedAnyQuiz(String studentId) throws IOException {
        return QuizServices.hasStudentPassedAnyQuiz(studentId);
    }

    public static void assignQuizToLesson(String lessonId, Quiz quiz) throws IOException {
        Lesson lesson = findLessonById(lessonId);
        if (lesson != null) {
            lesson.setQuizId(quiz.getQuizId());
            JsonHandler.saveCourses();
        }
    }
    
    public static Quiz getQuizByLessonId(String lessonId) throws IOException {
        Lesson lesson = findLessonById(lessonId);
        if (lesson != null && lesson.getQuizId() != null) {
            return null;
        }
        return null;
    }
    
    public static void completeLessonViaQuiz(String studentId, String lessonId) throws IOException {
        markLessonCompleted(studentId, lessonId);
        System.out.println("Lesson " + lessonId + " completed automatically for student " + studentId);
    }
    
    public static boolean canTakeQuiz(String studentId, String lessonId) throws IOException {
        return QuizServices.getRemainingAttempts(studentId, lessonId) > 0;
    }
    
    public static boolean isLessonCompleted(String studentId, String lessonId) throws IOException {
        if (QuizServices.isLessonCompletedViaQuiz(studentId, lessonId)) {
            return true;
        }
        
        try {
            Course course = findCourseByLessonId(lessonId);
            if (course != null) {
                return checkLessonCompletedInEnrollments(studentId, course.getCourseId(), lessonId);
            }
        } catch (Exception e) {
        }
        
        return false;
    }
    
    private static boolean checkLessonCompletedInEnrollments(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseId().equals(courseId)) {
                return enrollment.getCompletedLessons().contains(lessonId);
            }
        }
        return false;
    }
    
    public static void markLessonAsComplete(String studentId, String lessonId) throws IOException {
        markLessonCompleted(studentId, lessonId);
    }
    
    public static Quiz getQuizForLesson(String lessonId) throws IOException {
        return createSampleQuizForLesson(lessonId);
    }
    
    private static Quiz createSampleQuizForLesson(String lessonId) {
        try {
            ArrayList<Question> questions = new ArrayList<>();
            
            questions.add(QuizServices.createQuestion(
                "What did you learn in this lesson?",
                new String[]{"Nothing", "Something", "Everything", "Not sure"},
                1,
                "You should have learned something from this lesson"
            ));
            
            questions.add(QuizServices.createQuestion(
                "Is this lesson important?",
                new String[]{"No", "Maybe", "Yes", "I don't know"},
                2,
                "All lessons are important for your learning"
            ));
            
            return QuizServices.createQuiz(lessonId, questions);
            
        } catch (IOException e) {
            System.out.println("Error creating sample quiz: " + e.getMessage());
            return new Quiz(lessonId);
        }
    }
    
    public static Lesson addLessonToCourse(String courseId, String lessonTitle, String lessonContent) 
            throws IOException {
        Lesson lesson = createLesson(lessonTitle, lessonContent);
        return addLessonToCourse(courseId, lesson);
    }
}