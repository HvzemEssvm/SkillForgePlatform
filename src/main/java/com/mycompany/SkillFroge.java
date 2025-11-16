package com.mycompany;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.CourseServices;
import com.mycompany.CourseManagement.Lesson;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class SkillFroge {

    public static void main(String[] args) throws IOException {
        CourseServices courseManagment = new CourseServices();
        courseManagment.createCourse("instructor1", "Title1", "description 1");
        courseManagment.createCourse("instructor2", "Title2", "description 2");
        courseManagment.createCourse("instructor3", "Title3", "description 3");
        courseManagment.createCourse("instructor4", "Title4", "description 4");
        courseManagment.createCourse("instructor5", "Title5", "description 5");
        System.out.println(courseManagment.findCourseById(3).toString());
        courseManagment.deleteCourseById(4);
        System.out.println(courseManagment.updateCourse(5, "newDescription", "newTitle").toString());

          ArrayList<Course> courses = courseManagment.getAllCourses();
          ArrayList<Lesson> lessons = courseManagment.getAllLessonsFromCourse(2);
          
          for (Lesson lesson : lessons) {
              System.out.println(lesson.toString());
        }
          for (Course course : courses) {
              System.out.println(course.toString());
        }

            Lesson lesson = courseManagment.createLesson("lesson one", "content one");
            courseManagment.addLessonToCourse(1, lesson);
            
            courseManagment.updateLessonById(2, "newTitle", "newContent");
            courseManagment.deleteLessonById(3);

              courseManagment.enrollStudentInCourse(2, "st1");
              courseManagment.enrollStudentInCourse(2, "st1");
              courseManagment.enrollStudentInCourse(2, "st2");
              courseManagment.enrollStudentInCourse(3, "st2");

                System.out.println(courseManagment.getEnrolledStudents(2));
    }

}
