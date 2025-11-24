package com.mycompany.CourseManagement;

import com.mycompany.JsonHandler.JsonHandler;
import com.mycompany.QuizManagement.Question;
import com.mycompany.QuizManagement.Quiz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizServices {
    private static final String QUIZ_RESULTS_FILE = "quiz_results.json";

    public static Quiz createQuiz(String lessonId, ArrayList<Question> questions) throws IOException {
        Quiz quiz = new Quiz(lessonId, questions, 70);
        return quiz;
    }

    public static Question createQuestion(String questionText, String[] options, int correctIndex) {
        ArrayList<String> optionsList = new ArrayList<>();
        for (String option : options) {
            optionsList.add(option);
        }
        return new Question(questionText, optionsList, correctIndex);
    }

    public static void submitQuizResult(String studentId, String quizId, int score, boolean passed) throws IOException {
        JsonHandler.initializeFileIfNeeded(QUIZ_RESULTS_FILE);
        var resultsArray = JsonHandler.readArrayFromFile(QUIZ_RESULTS_FILE);

        Map<String, Object> result = new HashMap<>();
        result.put("resultId", "R" + System.currentTimeMillis());
        result.put("studentId", studentId);
        result.put("quizId", quizId);
        result.put("score", score);
        result.put("passed", passed);
        result.put("attemptDate", System.currentTimeMillis());

        String lessonId = extractLessonIdFromQuizId(quizId);
        result.put("lessonId", lessonId);

        resultsArray.add(JsonHandler.objectMapper.valueToTree(result));
        JsonHandler.writeToFile(resultsArray, QUIZ_RESULTS_FILE);

        System.out.println("=== QUIZ RESULT SAVED ===");
        System.out.println("Student: " + studentId);
        System.out.println("Lesson: " + lessonId);
        System.out.println("Quiz: " + quizId);
        System.out.println("Score: " + score + ", Passed: " + passed);
    }

    private static String extractLessonIdFromQuizId(String quizId) {
        if (quizId.contains("_L")) {
            String[] parts = quizId.split("_");
            if (parts.length >= 2 && parts[1].startsWith("L")) {
                return parts[1];
            }
        }

        if (quizId.contains("L1") || quizId.startsWith("QZ1"))
            return "L1";
        if (quizId.contains("L2") || quizId.startsWith("QZ2"))
            return "L2";
        if (quizId.contains("L3") || quizId.startsWith("QZ3"))
            return "L3";

        return "L1";
    }

    public static ArrayList<Map<String, Object>> getStudentQuizResults(String studentId) throws IOException {
        JsonHandler.initializeFileIfNeeded(QUIZ_RESULTS_FILE);
        var resultsArray = JsonHandler.readArrayFromFile(QUIZ_RESULTS_FILE);

        ArrayList<Map<String, Object>> studentResults = new ArrayList<>();

        for (var resultNode : resultsArray) {
            if (resultNode.get("studentId").asText().equals(studentId)) {
                Map<String, Object> result = new HashMap<>();
                result.put("quizId", resultNode.get("quizId").asText());
                result.put("score", resultNode.get("score").asInt());
                result.put("passed", resultNode.get("passed").asBoolean());
                result.put("attemptDate", resultNode.get("attemptDate").asLong());

                String lessonId;
                if (resultNode.has("lessonId")) {
                    lessonId = resultNode.get("lessonId").asText();
                } else {
                    lessonId = extractLessonIdFromQuizId(resultNode.get("quizId").asText());
                }
                result.put("lessonId", lessonId);

                studentResults.add(result);
            }
        }

        return studentResults;
    }

    public static int getRemainingAttempts(String studentId, String lessonId) throws IOException {
        ArrayList<Map<String, Object>> results = getStudentQuizResults(studentId);

        System.out.println("=== CHECKING ATTEMPTS ===");
        System.out.println("Student: " + studentId);
        System.out.println("Lesson: " + lessonId);
        System.out.println("Total results found: " + (results != null ? results.size() : 0));

        if (results == null || results.isEmpty()) {
            System.out.println("No attempts found - 3 attempts remaining");
            return 3;
        }

        int attemptCount = 0;

        for (Map<String, Object> result : results) {
            String resultLessonId = String.valueOf(result.get("lessonId"));
            String quizId = String.valueOf(result.get("quizId"));
            boolean passed = (boolean) result.get("passed");

            System.out.println("Checking: Quiz=" + quizId + ", Lesson=" + resultLessonId + ", Passed=" + passed);

            if (resultLessonId.equals(lessonId)) {
                attemptCount++;
                System.out.println("✅ MATCH! Attempt #" + attemptCount + " for lesson " + lessonId);
            }
        }

        int remaining = Math.max(0, 3 - attemptCount);
        System.out.println("📊 FINAL: " + lessonId + " - Used: " + attemptCount + ", Remaining: " + remaining);

        return remaining;
    }

    public static int getUsedAttempts(String studentId, String lessonId) throws IOException {
        ArrayList<Map<String, Object>> results = getStudentQuizResults(studentId);
        if (results == null || results.isEmpty()) {
            return 0;
        }

        int attemptCount = 0;

        for (Map<String, Object> result : results) {
            String resultLessonId = String.valueOf(result.get("lessonId"));
            if (resultLessonId.equals(lessonId)) {
                attemptCount++;
            }
        }

        return attemptCount;
    }

    public static boolean isLessonCompletedViaQuiz(String studentId, String lessonId) throws IOException {
        var results = getStudentQuizResults(studentId);

        for (var result : results) {
            String resultLessonId = String.valueOf(result.get("lessonId"));
            boolean passed = (boolean) result.get("passed");

            if (resultLessonId.equals(lessonId) && passed) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasStudentPassedAnyQuiz(String studentId) throws IOException {
        ArrayList<Map<String, Object>> results = getStudentQuizResults(studentId);
        if (results == null || results.isEmpty()) {
            return false;
        }

        for (Map<String, Object> result : results) {
            boolean passed = (boolean) result.get("passed");
            if (passed) {
                return true;
            }
        }

        return false;
    }

    public static void resetAllAttempts() throws IOException {
        JsonHandler.initializeFileIfNeeded(QUIZ_RESULTS_FILE);
        var emptyArray = JsonHandler.objectMapper.createArrayNode();
        JsonHandler.writeToFile(emptyArray, QUIZ_RESULTS_FILE);
        System.out.println("ALL ATTEMPTS RESET - Start fresh with 3 attempts each");
    }
}