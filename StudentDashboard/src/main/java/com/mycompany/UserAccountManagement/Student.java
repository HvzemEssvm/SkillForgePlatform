/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

/**
 *
 * @author Hazem
 */
public class Student extends User {
    
    public Student(String userId, String name, String email, String password) throws IllegalArgumentException
    {
        super(userId, name, email, password);    
    }
    
}
