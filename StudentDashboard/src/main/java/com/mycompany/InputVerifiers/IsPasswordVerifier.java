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
public class IsPasswordVerifier extends InputVerifier {
    private final String fieldName;
    public IsPasswordVerifier(String fieldName)
    {
        this.fieldName = fieldName;
    }
    @Override
    public boolean verify(JComponent input)
    {
        JTextField field = (JTextField) input;
        if(Validation.isPassword(field.getText()))
        {
            return true;
        }
        JOptionPane.showMessageDialog(input, fieldName+" length MUST be >= 8, password MUST NOT contain whitespaces", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
