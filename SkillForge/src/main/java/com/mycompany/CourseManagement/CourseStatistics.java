package com.mycompany.CourseManagement;

/**
 * Data Transfer Objects for Performance Analytics
 * These classes structure the data for easy display in UI
 */

/**
 * Overall course statistics
 */
public class CourseStatistics {
    private String courseId;
    private int totalStudents;
    private int totalLessons;
    private double averageScore;
    private double completionRate;
    private int studentsPassedAll;

    public CourseStatistics(String courseId, int totalStudents, int totalLessons,
                           double averageScore, double completionRate, int studentsPassedAll) {
        this.courseId = courseId;
        this.totalStudents = totalStudents;
        this.totalLessons = totalLessons;
        this.averageScore = averageScore;
        this.completionRate = completionRate;
        this.studentsPassedAll = studentsPassedAll;
    }

    // Getters
    public String getCourseId() { return courseId; }
    public int getTotalStudents() { return totalStudents; }
    public int getTotalLessons() { return totalLessons; }
    public double getAverageScore() { return averageScore; }
    public double getCompletionRate() { return completionRate; }
    public int getStudentsPassedAll() { return studentsPassedAll; }

    @Override
    public String toString() {
        return String.format("CourseStatistics[Students=%d, Lessons=%d, AvgScore=%.1f%%, Completion=%.1f%%]",
                totalStudents, totalLessons, averageScore, completionRate);
    }
}

/**
 * Performance metrics for a single lesson
 */
class LessonPerformance {
    private String lessonId;
    private String lessonTitle;
    private double averageScore;
    private int studentsCompleted;
    private int studentsPassed;

    public LessonPerformance(String lessonId, String lessonTitle, double averageScore,
                            int studentsCompleted, int studentsPassed) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.averageScore = averageScore;
        this.studentsCompleted = studentsCompleted;
        this.studentsPassed = studentsPassed;
    }

    // Getters
    public String getLessonId() { return lessonId; }
    public String getLessonTitle() { return lessonTitle; }
    public double getAverageScore() { return averageScore; }
    public int getStudentsCompleted() { return studentsCompleted; }
    public int getStudentsPassed() { return studentsPassed; }

    @Override
    public String toString() {
        return String.format("LessonPerformance[%s: AvgScore=%.1f%%, Completed=%d, Passed=%d]",
                lessonTitle, averageScore, studentsCompleted, studentsPassed);
    }
}

/**
 * Individual student performance in a course
 */
class StudentPerformance {
    private String studentId;
    private String studentName;
    private String courseId;
    private double averageScore;
    private int completedLessons;
    private int totalLessons;
    private double progressPercentage;

    public StudentPerformance(String studentId, String studentName, String courseId,
                             double averageScore, int completedLessons, int totalLessons,
                             double progressPercentage) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.averageScore = averageScore;
        this.completedLessons = completedLessons;
        this.totalLessons = totalLessons;
        this.progressPercentage = progressPercentage;
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseId() { return courseId; }
    public double getAverageScore() { return averageScore; }
    public int getCompletedLessons() { return completedLessons; }
    public int getTotalLessons() { return totalLessons; }
    public double getProgressPercentage() { return progressPercentage; }

    @Override
    public String toString() {
        return String.format("StudentPerformance[%s: AvgScore=%.1f%%, Progress=%.1f%%]",
                studentName, averageScore, progressPercentage);
    }
}