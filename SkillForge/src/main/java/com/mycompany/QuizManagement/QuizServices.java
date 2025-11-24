package com.mycompany.QuizManagement;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseProgress;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Student;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Quiz operations
 */
public class QuizServices {

    /**
     * Create a quiz with questions
     * 
     * @param quizId
     * @param questions    list of questions
     * @param passingScore minimum score to pass (percentage)
     * @return created Quiz object
     */
    public static Quiz createQuiz(String quizId, ArrayList<Question> questions, int passingScore) {
        return new Quiz(quizId, questions, passingScore);
    }

    /**
     * Add quiz to a lesson
     * 
     * @param courseId the course containing the lesson
     * @param lessonId the lesson to add quiz
     * @param quiz     the quiz to add
     * @throws IOException
     */
    public static void addQuizToLesson(String courseId, String lessonId, Quiz quiz) throws IOException {
        Lesson lesson = CourseServices.findLessonById(courseId, lessonId);
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson not found");
        }

        lesson.setQuiz(quiz);
        JsonHandler.saveCourses();
    }

    /**
     * Submit quiz answers and calculate score
     * 
     * @param studentId      the student taking the quiz
     * @param courseId       the course containing the lesson
     * @param lessonId       the lesson with the quiz
     * @param studentAnswers list of answer indices selected by student
     * @return QuizResult with score and pass/fail status
     * @throws IOException
     */
    public static QuizResult submitQuiz(String studentId, String courseId, String lessonId,
            ArrayList<Integer> studentAnswers) throws IOException {

        // Get the lesson and its quiz
        Lesson lesson = CourseServices.findLessonById(courseId, lessonId);
        if (lesson == null || lesson.getQuiz() == null) {
            throw new IllegalArgumentException("Lesson or quiz not found");
        }

        Quiz quiz = lesson.getQuiz();

        // Calculate score
        int correctAnswers = 0;
        ArrayList<Question> questions = quiz.getQuestions();

        for (int i = 0; i < questions.size() && i < studentAnswers.size(); i++) {
            if (questions.get(i).isCorrect(studentAnswers.get(i))) {
                correctAnswers++;
            }
        }

        int scorePercentage = (correctAnswers * 100) / questions.size();
        boolean passed = scorePercentage >= quiz.getPassingScore();

        // Get student and their course progress
        Student student = JsonHandler.getStudent(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        // Get or create course progress
        CourseProgress progress = student.getCourseProgress(courseId);
        if (progress == null) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }

        // Determine attempt number
        int attemptNumber = 1;
        List<QuizAttempt> attempts = progress.getQuizAttempts();
        for (QuizAttempt attempt : attempts) {
            if (attempt.getLessonId().equals(lessonId)) {
                attemptNumber++;
            }
        }

        // Create quiz attempt for CourseProgress
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuizId(quiz.getQuizId());
        quizAttempt.setLessonId(lessonId);
        quizAttempt.setCourseId(courseId);
        quizAttempt.setScorePercent(scorePercentage);
        quizAttempt.setPassed(passed);
        quizAttempt.setAttemptTime(Instant.now().toString());

        // Add to course progress (automatically updates lesson status and scores)
        progress.addQuizAttempt(quizAttempt);

        // Save changes
        JsonHandler.saveUsers();

        // Update quiz average score
        updateQuizAverageScore(courseId, lessonId);

        // Create QuizResult for backward compatibility
        QuizResult result = new QuizResult(lessonId, scorePercentage, passed, attemptNumber, studentAnswers);
        return result;
    }

    /**
     * Get all quiz results for a student in a specific lesson
     * 
     * @param studentId the student ID
     * @param courseId  the course ID
     * @param lessonId  the lesson ID
     * @return list of quiz results (converted from QuizAttempts)
     */
    public static ArrayList<QuizResult> getQuizResultsForLesson(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);
        ArrayList<QuizResult> results = new ArrayList<>();

        // Return empty list if student not found
        if (student == null) {
            return results;
        }

        // Get course progress
        CourseProgress progress = student.getCourseProgress(courseId);
        if (progress == null) {
            return results;
        }

        // Convert QuizAttempts to QuizResults for this lesson
        int attemptNum = 1;
        for (QuizAttempt attempt : progress.getQuizAttempts()) {
            if (attempt.getLessonId().equals(lessonId)) {
                QuizResult result = new QuizResult(
                        lessonId,
                        attempt.getScorePercent(),
                        attempt.isPassed(),
                        attemptNum++,
                        new ArrayList<>() // Student answers not stored in QuizAttempt
                );
                results.add(result);
            }
        }

        return results;
    }

    /**
     * Get the best quiz score for a lesson
     * 
     * @param studentId the student ID
     * @param courseId  the course ID
     * @param lessonId  the lesson ID
     * @return best score or -1 if no attempts
     */
    public static int getBestScore(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);
        if (student == null) {
            return -1;
        }

        CourseProgress progress = student.getCourseProgress(courseId);
        if (progress == null) {
            return -1;
        }

        int bestScore = -1;
        for (QuizAttempt attempt : progress.getQuizAttempts()) {
            if (attempt.getLessonId().equals(lessonId)) {
                if (attempt.getScorePercent() > bestScore) {
                    bestScore = attempt.getScorePercent();
                }
            }
        }

        return bestScore;
    }

    /**
     * Check if student has passed the quiz for a lesson
     * 
     * @param studentId the student ID
     * @param courseId  the course ID
     * @param lessonId  the lesson ID
     * @return true if passed, false otherwise
     */
    public static boolean hasPassedQuiz(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);
        if (student == null) {
            return false;
        }

        CourseProgress progress = student.getCourseProgress(courseId);
        if (progress == null) {
            return false;
        }

        // Check if lesson status is PASSED
        String status = progress.getLessonStatus().get(lessonId);
        return "PASSED".equals(status);
    }

    /**
     * Update the average score for a quiz across all students
     * 
     * @param courseId the course ID
     * @param lessonId the lesson ID with the quiz
     * @throws IOException
     */
    private static void updateQuizAverageScore(String courseId, String lessonId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);
        Lesson lesson = CourseServices.findLessonById(courseId, lessonId);

        if (lesson == null || lesson.getQuiz() == null) {
            return;
        }

        // Get all enrolled students
        ArrayList<String> studentIds = course.getStudentIds();
        int totalScore = 0;
        int studentCount = 0;

        // Calculate average across all students (best score for each)
        for (String studentId : studentIds) {
            int bestScore = getBestScore(studentId, courseId, lessonId);
            if (bestScore >= 0) {
                totalScore += bestScore;
                studentCount++;
            }
        }

        if (studentCount > 0) {
            double averageScore = (double) totalScore / studentCount;
            lesson.getQuiz().setAverageScore(Math.round(averageScore * 100.0) / 100.0);
            JsonHandler.saveCourses();
        }
    }
}