package com.mycompany;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import com.mycompany.CourseManagement.CourseServices;
import java.io.IOException;

/**
 *
 * @author Mega
 */
public class SkillFroge {

    public static void main(String[] args) throws IOException {
        CourseServices courseManagment = new CourseServices();
        courseManagment.createCourse("instructor1", "Title1", "description 1");
        courseManagment.createCourse("instructor2", "Title2", "description 2");
        courseManagment.createCourse("instructor3", "Title3", "description 3");
        courseManagment.createCourse("instructor4", "Title4", "description 4");
        courseManagment.createCourse("instructor5", "Title5", "description 5");
    }

}
