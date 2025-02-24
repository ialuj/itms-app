/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.users;

import com.hannsoftware.itms.app.api.UserServices;
import com.hannsoftware.itms.app.enums.Role;
import com.hannsoftware.itms.app.form.main.MainForm;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserRegistrationForm extends JFrame {
    public UserRegistrationForm() {
        setTitle("IT Support Ticket Management System");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[][]20[][]20[]"));

        JLabel lblTitle = new JLabel("User Registration", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(16f));

        JLabel lblFullName = new JLabel("Full Name:");
        JTextField txtFullName = new JTextField(20);
        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);
        JLabel lblRole = new JLabel("Role:");
        String[] roles = new String[2];
        int position = 0;
        for(Role role: Role.values()) {
            roles[position] = role.getDescription();
            position++;
        }
        JComboBox<String> comboRole = new JComboBox<>(roles);

        JButton btnRegister = new JButton("Register");
        JButton btnClear = new JButton("Clear Fields");
        JButton btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(e -> {
            new MainForm();
            dispose();
                });

        btnClear.addActionListener(e -> {
            txtFullName.setText("");
            txtUsername.setText("");
            txtPassword.setText("");
            comboRole.setSelectedIndex(0);
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = txtFullName.getText();
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                String role = (String) comboRole.getSelectedItem();

                if (StringUtils.isBlank(fullName) || fullName.split(" ").length < 2) {
                    showDialogMessage("Error", "Fullname must not be empty and must have at least 2 names.", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (StringUtils.isBlank(username) || username.length() < 5) {
                    showDialogMessage("Error", "Username must not be empty and must have at least 5 characters.",  JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (StringUtils.isBlank(password) || password.length() < 5) {
                    showDialogMessage("Error", "Password must not be empty and must have at least 5 characters.", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String response = UserServices.registerUser(fullName, username, password, role);
                if("200".equals(response)) {
                    showDialogMessage("Success", "User registered successful!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showDialogMessage("Error", "An error occurred while registering the User, please try again later. If the error persistes, contact the IT Support Team!", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        add(lblTitle, "span, align center");
        add(lblFullName);
        add(txtFullName, "wrap");
        add(lblUsername);
        add(txtUsername, "wrap");
        add(lblPassword);
        add(txtPassword, "wrap");
        add(lblRole);
        add(comboRole, "wrap");
        add(btnRegister, "split 4, center");
        add(btnClear);
        add(btnCancel);

        setVisible(true);
    }

    private void showDialogMessage(String messageType, String message, int messageCode) {
        JOptionPane.showMessageDialog(this, message, messageType, messageCode);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserRegistrationForm::new);
    }
}
