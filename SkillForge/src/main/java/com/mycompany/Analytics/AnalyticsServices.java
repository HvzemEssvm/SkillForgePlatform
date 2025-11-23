package com.mycompany.Analytics;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.QuizManagement.QuizResult;
import com.mycompany.QuizManagement.QuizServices;
import com.mycompany.UserAccountManagement.Enrollment;
import com.mycompany.UserAccountManagement.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for Analytics and Performance Insights
 */
public class AnalyticsServices {

    /**
     * Get comprehensive analytics for a course (for instructors)
     * 
     * @param courseId the course to analyze
     * @return CourseAnalytics object with all insights
     * @throws IOException
     */
    public static CourseAnalytics getCourseAnalytics(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        CourseAnalytics analytics = new CourseAnalytics();
        analytics.setCourseId(courseId);
        analytics.setCourseTitle(course.getTitle());

        ArrayList<String> studentIds = course.getStudentIds();
        analytics.setTotalStudents(studentIds.size());

        if (studentIds.isEmpty()) {
            return analytics; // No students enrolled
        }

        // Aggregate student data
        int activeStudentCount = 0;
        double totalCompletionPercentage = 0;
        double totalScore = 0;
        int studentsWithScores = 0;

        ArrayList<StudentPerformance> studentPerformances = new ArrayList<>();

        for (String studentId : studentIds) {
            Student student = JsonHandler.getStudent(studentId);
            Enrollment enrollment = getEnrollmentForCourse(student, courseId);

            if (enrollment != null) {
                // Check if student is active
                if (!enrollment.getCompletedLessons().isEmpty()) {
                    activeStudentCount++;
                }

                // Parse completion percentage
                String completionStr = enrollment.getCompletionPercentage().replace("%", "");
                double completion = Double.parseDouble(completionStr);
                totalCompletionPercentage += completion;

                // Add average score
                if (enrollment.getAverageScore() > 0) {
                    totalScore += enrollment.getAverageScore();
                    studentsWithScores++;
                }

                // Build student performance data
                StudentPerformance sp = buildStudentPerformance(student, enrollment, course);
                studentPerformances.add(sp);
            }
        }

        analytics.setActiveStudents(activeStudentCount);

        if (studentIds.size() > 0) {
            analytics.setAverageCompletionPercentage(
                    Math.round((totalCompletionPercentage / studentIds.size()) * 100.0) / 100.0);
        }

        if (studentsWithScores > 0) {
            analytics.setAverageScore(
                    Math.round((totalScore / studentsWithScores) * 100.0) / 100.0);
        }

        analytics.setStudentPerformances(studentPerformances);

        // Build lesson analytics
        ArrayList<LessonAnalytics> lessonAnalyticsList = buildLessonAnalytics(course, studentIds);
        analytics.setLessonAnalytics(lessonAnalyticsList);

        return analytics;
    }

    /**
     * Get analytics for a specific lesson
     * 
     * @param courseId the course ID
     * @param lessonId the lesson ID
     * @return LessonAnalytics object
     * @throws IOException
     */
    public static LessonAnalytics getLessonAnalytics(String courseId, String lessonId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);
        Lesson lesson = CourseServices.findLessonById(courseId, lessonId);

        if (course == null || lesson == null) {
            throw new IllegalArgumentException("Course or lesson not found");
        }

        LessonAnalytics analytics = new LessonAnalytics();
        analytics.setLessonId(lessonId);
        analytics.setLessonTitle(lesson.getTitle());

        ArrayList<String> studentIds = course.getStudentIds();

        int studentsCompleted = 0;
        int studentsAttempted = 0;
        int totalAttempts = 0;
        int totalScore = 0;
        int studentsPassed = 0;

        for (String studentId : studentIds) {
            Student student = JsonHandler.getStudent(studentId);
            Enrollment enrollment = getEnrollmentForCourse(student, courseId);

            if (enrollment != null) {
                // Check completion
                if (enrollment.getCompletedLessons().contains(lessonId)) {
                    studentsCompleted++;
                }

                // Check quiz attempts
                ArrayList<QuizResult> results = QuizServices.getQuizResultsForLesson(studentId, courseId, lessonId);
                if (!results.isEmpty()) {
                    studentsAttempted++;
                    totalAttempts += results.size();

                    // Get best score
                    int bestScore = QuizServices.getBestScore(studentId, courseId, lessonId);
                    if (bestScore >= 0) {
                        totalScore += bestScore;
                    }

                    // Check if passed
                    if (QuizServices.hasPassedQuiz(studentId, courseId, lessonId)) {
                        studentsPassed++;
                    }
                }
            }
        }

        analytics.setStudentsCompleted(studentsCompleted);
        analytics.setStudentsAttempted(studentsAttempted);
        analytics.setTotalAttempts(totalAttempts);

        if (studentsAttempted > 0) {
            analytics.setAverageScore(
                    Math.round(((double) totalScore / studentsAttempted) * 100.0) / 100.0);
            analytics.setPassRate(
                    Math.round(((double) studentsPassed / studentsAttempted * 100) * 100.0) / 100.0);
        }

        return analytics;
    }

    /**
     * Get performance data for a specific student in a course
     * 
     * @param studentId the student ID
     * @param courseId  the course ID
     * @return StudentPerformance object
     * @throws IOException
     */
    public static StudentPerformance getStudentPerformance(String studentId, String courseId) throws IOException {
        Student student = JsonHandler.getStudent(studentId);
        Course course = CourseServices.findCourseById(courseId);

        if (student == null || course == null) {
            throw new IllegalArgumentException("Student or course not found");
        }

        Enrollment enrollment = getEnrollmentForCourse(student, courseId);
        if (enrollment == null) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }

        return buildStudentPerformance(student, enrollment, course);
    }

    /**
     * Get quiz performance comparison across all students for a lesson
     * 
     * @param courseId the course ID
     * @param lessonId the lesson ID
     * @return Map of studentId -> best score
     * @throws IOException
     */
    public static Map<String, Integer> getQuizPerformanceComparison(String courseId, String lessonId)
            throws IOException {
        Course course = CourseServices.findCourseById(courseId);
        Map<String, Integer> performanceMap = new HashMap<>();

        for (String studentId : course.getStudentIds()) {
            int bestScore = QuizServices.getBestScore(studentId, courseId, lessonId);
            if (bestScore >= 0) {
                performanceMap.put(studentId, bestScore);
            }
        }

        return performanceMap;
    }

    /**
     * Get completion rate per lesson
     * 
     * @param courseId the course ID
     * @return Map of lessonId -> completion percentage
     * @throws IOException
     */
    public static Map<String, Double> getCompletionRatePerLesson(String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);
        Map<String, Double> completionMap = new HashMap<>();

        ArrayList<String> studentIds = course.getStudentIds();
        int totalStudents = studentIds.size();

        if (totalStudents == 0) {
            return completionMap;
        }

        for (Lesson lesson : course.getLessons()) {
            int completedCount = 0;

            for (String studentId : studentIds) {
                if (CourseServices.isLessonCompleted(studentId, courseId, lesson.getLessonId())) {
                    completedCount++;
                }
            }

            double completionRate = ((double) completedCount / totalStudents) * 100;
            completionMap.put(lesson.getLessonId(),
                    Math.round(completionRate * 100.0) / 100.0);
        }

        return completionMap;
    }

    // ============= Helper Methods =============

    /**
     * Build lesson analytics for all lessons in a course
     */
    private static ArrayList<LessonAnalytics> buildLessonAnalytics(Course course, ArrayList<String> studentIds)
            throws IOException {
        ArrayList<LessonAnalytics> analyticsList = new ArrayList<>();

        for (Lesson lesson : course.getLessons()) {
            LessonAnalytics la = getLessonAnalytics(course.getCourseId(), lesson.getLessonId());
            analyticsList.add(la);
        }

        return analyticsList;
    }

    /**
     * Build student performance object
     */
    private static StudentPerformance buildStudentPerformance(Student student, Enrollment enrollment, Course course)
            throws IOException {
        StudentPerformance sp = new StudentPerformance();

        sp.setStudentId(student.getUserId());
        sp.setStudentName(student.getName());
        sp.setCompletedLessons(enrollment.getCompletedLessons().size());
        sp.setTotalLessons(course.getLessons().size());

        // Parse completion percentage
        String completionStr = enrollment.getCompletionPercentage().replace("%", "");
        sp.setCompletionPercentage(Double.parseDouble(completionStr));

        sp.setAverageScore(enrollment.getAverageScore());
        sp.setTotalQuizAttempts(enrollment.getQuizResults().size());

        // Build lesson progress
        ArrayList<LessonProgress> lessonProgressList = new ArrayList<>();
        for (Lesson lesson : course.getLessons()) {
            LessonProgress lp = new LessonProgress();
            lp.setLessonId(lesson.getLessonId());
            lp.setLessonTitle(lesson.getTitle());
            lp.setCompleted(enrollment.getCompletedLessons().contains(lesson.getLessonId()));

            ArrayList<QuizResult> results = QuizServices.getQuizResultsForLesson(
                    student.getUserId(), course.getCourseId(), lesson.getLessonId());
            lp.setAttempts(results.size());

            int bestScore = QuizServices.getBestScore(
                    student.getUserId(), course.getCourseId(), lesson.getLessonId());
            lp.setBestScore(bestScore >= 0 ? bestScore : 0);

            lessonProgressList.add(lp);
        }
        sp.setLessonProgress(lessonProgressList);

        return sp;
    }

    /**
     * Get enrollment for a specific course
     */
    private static Enrollment getEnrollmentForCourse(Student student, String courseId) {
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseId().equals(courseId)) {
                return enrollment;
            }
        }
        return null;
    }
}