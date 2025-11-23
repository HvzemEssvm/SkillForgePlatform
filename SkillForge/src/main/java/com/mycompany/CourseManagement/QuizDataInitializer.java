/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CourseManagement;

/**
 *
 * @author afifi.store
 */


import java.io.IOException;
import java.util.ArrayList;

public class QuizDataInitializer {
    
    public static void initializeSampleQuizzes() throws IOException {
        // عمل كويز للدرس الأول
        ArrayList<Question> questions1 = new ArrayList<>();
        
        questions1.add(QuizServices.createQuestion(
            "What is Object-Oriented Programming?",
            new String[]{
                "A type of database",
                "A programming paradigm based on objects", 
                "A web development framework",
                "A version control system"
            },
            1,
            "OOP is a programming paradigm based on the concept of objects"
        ));
        
        questions1.add(QuizServices.createQuestion(
            "Which of these is NOT an OOP principle?",
            new String[]{
                "Inheritance",
                "Polymorphism", 
                "Encapsulation",
                "Compilation"
            },
            3,
            "Compilation is not an OOP principle"
        ));
        
        Quiz quiz1 = QuizServices.createQuiz("L1", questions1);
        CourseServices.assignQuizToLesson("L1", quiz1);
        
        // عمل كويز للدرس الثاني
        ArrayList<Question> questions2 = new ArrayList<>();
        
        questions2.add(QuizServices.createQuestion(
            "What is Java?",
            new String[]{
                "A coffee brand",
                "An island in Indonesia", 
                "A programming language",
                "A type of computer"
            },
            2,
            "Java is a high-level programming language"
        ));
        
        questions2.add(QuizServices.createQuestion(
            "Which company created Java?",
            new String[]{
                "Microsoft",
                "Apple", 
                "Sun Microsystems",
                "Google"
            },
            2,
            "Java was created by Sun Microsystems"
        ));
        
        Quiz quiz2 = QuizServices.createQuiz("L2", questions2);
        CourseServices.assignQuizToLesson("L2", quiz2);
    }

}