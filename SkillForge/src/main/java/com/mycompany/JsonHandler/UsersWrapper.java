/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.JsonHandler;

import com.mycompany.UserAccountManagement.Instructor;
import com.mycompany.UserAccountManagement.Student;
import java.util.ArrayList;

/**
 *
 * @author Zeyad
 */
public class UsersWrapper {

    public ArrayList<Student> students;
    public ArrayList<Instructor> instructors;

    // Empty constructor for Jackson to deserialize JSON
    public UsersWrapper() {
    }

    public UsersWrapper(ArrayList<Student> students, ArrayList<Instructor> instructors) {
        this.students = students;
        this.instructors = instructors;
    }
}
