package com.mycompany.QuizManagement;

import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.UserAccountManagement.Enrollment;
import com.mycompany.UserAccountManagement.Student;
import java.io.IOException;
import java.util.ArrayList;

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

        // Get student and find their enrollment
        Student student = JsonHandler.getStudent(studentId);
        Enrollment enrollment = null;

        for (Enrollment enr : student.getEnrollments()) {
            if (enr.getCourseId().equals(courseId)) {
                enrollment = enr;
                break;
            }
        }

        if (enrollment == null) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }

        // Determine attempt number
        int attemptNumber = 1;
        for (QuizResult result : enrollment.getQuizResults()) {
            if (result.getLessonId().equals(lessonId)) {
                attemptNumber++;
            }
        }

        // Create quiz result
        QuizResult result = new QuizResult(lessonId, scorePercentage, passed, attemptNumber, studentAnswers);
        enrollment.addQuizResult(result);

        // If passed, mark lesson as completed
        if (passed && !enrollment.getCompletedLessons().contains(lessonId)) {
            enrollment.getCompletedLessons().add(lessonId);
        }

        // Update completion percentage and average score
        updateEnrollmentStats(enrollment, courseId);

        // Save changes
        JsonHandler.saveUsers();

        // Update quiz average score
        updateQuizAverageScore(courseId, lessonId);

        return result;
    }

    /**
     * Get all quiz results for a student in a specific lesson
     * 
     * @param studentId the student ID
     * @param courseId  the course ID
     * @param lessonId  the lesson ID
     * @return list of quiz results
     */
    public static ArrayList<QuizResult> getQuizResultsForLesson(String studentId, String courseId, String lessonId) {
        Student student = JsonHandler.getStudent(studentId);
        ArrayList<QuizResult> results = new ArrayList<>();

        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseId().equals(courseId)) {
                for (QuizResult result : enrollment.getQuizResults()) {
                    if (result.getLessonId().equals(lessonId)) {
                        results.add(result);
                    }
                }
                break;
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
        ArrayList<QuizResult> results = getQuizResultsForLesson(studentId, courseId, lessonId);
        int bestScore = -1;

        for (QuizResult result : results) {
            if (result.getScore() > bestScore) {
                bestScore = result.getScore();
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
        ArrayList<QuizResult> results = getQuizResultsForLesson(studentId, courseId, lessonId);

        for (QuizResult result : results) {
            if (result.isPassed()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Update enrollment statistics (completion and average score)
     * 
     * @param enrollment the enrollment to update
     * @param courseId   the course ID
     * @throws IOException
     */
    private static void updateEnrollmentStats(Enrollment enrollment, String courseId) throws IOException {
        Course course = CourseServices.findCourseById(courseId);

        // Calculate completion percentage
        int totalLessons = course.getLessons().size();
        int completedLessons = enrollment.getCompletedLessons().size();

        if (totalLessons > 0) {
            int completionPercentage = (completedLessons * 100) / totalLessons;
            enrollment.setCompletionPercentage(completionPercentage + "%");
        }

        // Calculate average score across all quizzes (using best attempt for each
        // lesson)
        ArrayList<QuizResult> results = enrollment.getQuizResults();
        if (!results.isEmpty()) {
            // Group by lesson and get best score for each
            ArrayList<String> processedLessons = new ArrayList<>();
            int totalScore = 0;
            int quizCount = 0;

            for (QuizResult result : results) {
                String lessonId = result.getLessonId();
                if (!processedLessons.contains(lessonId)) {
                    processedLessons.add(lessonId);

                    // Find best score for this lesson
                    int bestScore = result.getScore();
                    for (QuizResult r : results) {
                        if (r.getLessonId().equals(lessonId) && r.getScore() > bestScore) {
                            bestScore = r.getScore();
                        }
                    }

                    totalScore += bestScore;
                    quizCount++;
                }
            }

            if (quizCount > 0) {
                double averageScore = (double) totalScore / quizCount;
                enrollment.setAverageScore(Math.round(averageScore * 100.0) / 100.0);
            }
        }
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