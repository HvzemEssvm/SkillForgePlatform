/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.UserAccountManagement;

import com.mycompany.InputVerifiers.Validation;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * note that password must be >= 8 characters, and doesn't contain whitespace
 * @author Hazem
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    
    private static void  validateArgs(String userId, String name, String email, String password)
    {
        //Start of Validating that UserId is unique
        
        //End of Validating that UserId is unique

        if(!Validation.isEmail(email))
        {
            throw new IllegalArgumentException("Error: Invalid Email Input");
        }
        
        if(Validation.isAlphabetic(name) != 1)
        {
            throw new IllegalArgumentException("Error: Invalid Name Input");
        }
        
        if(!Validation.isPassword(password))
        {
            throw new IllegalArgumentException("Error: password length MUST be >= 8, password MUST NOT contain whitespaces");
        }
    }
    
    private static String getHashedPassword(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8)); // FYI: StandardCharsets.UTF_8 makes it standard output & consistent on all machines, References: https://medium.com/@AlexanderObregon/what-is-sha-256-hashing-in-java-0d46dfb83888
            String hexString = new String();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString = hexString.concat("0");
                }
                hexString = hexString.concat(hex);
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public User(String userId, String name, String email, String password) throws IllegalArgumentException
    {
        name = name.trim();
        email = email.trim();
        userId = userId.trim();
        password = password.trim();
        
        try
        {
            validateArgs(userId,name,email,password);
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.password = getHashedPassword(password);
        }
        catch(IllegalArgumentException e)
        {
                throw e;
        }
    }
    
//} // commented it to mark incompletness of the class
