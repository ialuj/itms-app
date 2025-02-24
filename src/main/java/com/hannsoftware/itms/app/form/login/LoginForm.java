/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.login;

import com.hannsoftware.itms.app.api.UserServices;
import com.hannsoftware.itms.app.domain.UserDTO;
import com.hannsoftware.itms.app.form.main.MainForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;

public class LoginForm extends JFrame {
        
    public LoginForm() {
        setTitle("IT Support Ticket Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[][]20[][]20[]"));

        JLabel lblTitle = new JLabel("Login", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(16f));

        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);

        JButton btnCancel = new JButton("Cancel");
        JButton btnLogin = new JButton("Login");

        add(lblTitle, "span, align center");
        add(lblUsername);
        add(txtUsername, "wrap");
        add(lblPassword);
        add(txtPassword, "wrap");
        add(btnCancel, "split 2, center");
        add(btnLogin);
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isUsernameFilled = isFieldEmpty(txtUsername.getText());
                if(isUsernameFilled) {
                    showDialogMessage("Error", "Username is required.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean isPasswordFilled = isFieldEmpty(txtPassword.getText());
                if(isPasswordFilled) {
                    showDialogMessage("Error", "Password is required.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                boolean isLoggedIn = login(txtUsername.getText(), txtPassword.getText());
                if(isLoggedIn) {
                    dispose();
                    new MainForm();
                }
                else {
                   showDialogMessage("Error", "There was an error while trying to Login, verify your Username or Password!", JOptionPane.ERROR_MESSAGE);
                   return; 
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
    
    private boolean isFieldEmpty(String value) {
        if(StringUtils.isBlank(value)) return true;
        return false;
    }
    
    private void showDialogMessage(String messageType, String message, int messageCode) {
        JOptionPane.showMessageDialog(this, message, messageType, messageCode);
    }
    
    private boolean login(String username, String password) {
        return UserServices.login(username, password);
    }
}

