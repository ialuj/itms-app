/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.users;

import com.hannsoftware.itms.app.api.UserServices;
import com.hannsoftware.itms.app.form.main.MainForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdatePasswordForm extends JFrame {

    private JTextField newPasswordField;
    private JTextField confirmPasswordField;

    public UpdatePasswordForm() {
        setTitle("Update Password");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField);

        formPanel.add(new JLabel("Confirm New Password:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this::handleSave);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainForm();
                dispose();
            }
        });
        
        cancelButton.addActionListener(e -> {
            new MainForm();
            dispose();
                });
        
        setVisible(true);
    }

    private void handleSave(ActionEvent e) {
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill both password fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = updatePassword(newPassword);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Password updated successfully.");
            new MainForm();
                dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean updatePassword(String newPassword) {
        return UserServices.updatePassword(newPassword);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdatePasswordForm::new);
    }
}

