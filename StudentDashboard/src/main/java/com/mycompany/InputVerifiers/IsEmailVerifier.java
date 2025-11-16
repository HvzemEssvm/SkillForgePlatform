/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.InputVerifiers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Hazem
 */
public class IsEmailVerifier extends InputVerifier {
    private final String fieldName;
    public IsEmailVerifier(String fieldName)
    {
        this.fieldName = fieldName;
    }
    @Override
    public boolean verify(JComponent input)
    {
        JTextField field = (JTextField) input;
        if(Validation.isEmail(field.getText()))
        {
            return true;
        }        
        
        JOptionPane.showMessageDialog(input, fieldName+" MUST be valid Email address", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
}
