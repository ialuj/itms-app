/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.tickets;

import com.hannsoftware.itms.app.api.TicketServices;
import com.hannsoftware.itms.app.enums.Category;
import com.hannsoftware.itms.app.enums.Priority;
import com.hannsoftware.itms.app.form.main.MainForm;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicketRegistrationForm extends JFrame {
    public TicketRegistrationForm() {
        setTitle("IT Support Ticket Management System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[][]20[][]20[]"));

        JLabel lblTitle = new JLabel("Ticket Registration", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(16f));

        JLabel lblTicketTitle = new JLabel("Title:");
        JTextField txtTicketTitle = new JTextField(20);
        JLabel lblDescription = new JLabel("Description:");
        JTextArea txtDescription = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);
        
        String[] priorities = new String[3];
        int priorityPosition = 0;
        for(Priority priority: Priority.values()) {
            priorities[priorityPosition] = priority.getDescription();
            priorityPosition++;
        }
        JLabel lblPriority = new JLabel("Priority:");
        JComboBox<String> comboPriority = new JComboBox<>(priorities);
        
        String[] categories = new String[4];
        int categoryPosition = 0;
        for(Category category: Category.values()) {
            categories[categoryPosition] = category.getDescription();
            categoryPosition++;
        }
        JLabel lblCategory = new JLabel("Category:");
        JComboBox<String> comboCategory = new JComboBox<>(categories);

        JButton btnRegister = new JButton("Register");
        JButton btnClear = new JButton("Clear Fields");
        JButton btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(e -> {
            new MainForm();
            dispose();
                });

        btnClear.addActionListener(e -> {
            txtTicketTitle.setText("");
            txtDescription.setText("");
            comboCategory.setSelectedIndex(0);
            comboPriority.setSelectedIndex(0);
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticketTitle = txtTicketTitle.getText();
                String description = txtDescription.getText();
                String category = (String) comboCategory.getSelectedItem();
                String priority = (String) comboPriority.getSelectedItem();

                if (StringUtils.isBlank(ticketTitle) || ticketTitle.length() < 10) {
                    showDialogMessage("Error", "The Title must not be empty and must have at least 10 characters.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(comboCategory.getSelectedItem() == null) {
                    showDialogMessage("Error", "Category is required.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(comboPriority.getSelectedItem() == null) {
                    showDialogMessage("Error", "Priority is required.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String responseCode = TicketServices.registerTicket(ticketTitle, description, priority, category);

                if(responseCode.equals("200")) {
                showDialogMessage("Sucess", "Ticket registered successful!", JOptionPane.ERROR_MESSAGE);
                } else {
                    showDialogMessage("Error", "An error occured while regstering the Ticket. Please try again later, if the error persist contact the System Administration Team!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainForm();
                dispose();
            }
        });

        add(lblTitle, "span, align center");
        add(lblTicketTitle);
        add(txtTicketTitle, "wrap");
        add(lblDescription);
        add(descriptionScroll, "wrap");
        add(lblCategory);
        add(comboCategory, "wrap");
        add(lblPriority);
        add(comboPriority, "wrap");
        add(btnRegister, "split 4, center");
        add(btnClear);
        add(btnCancel);

        setVisible(true);
    }

    private void showDialogMessage(String messageType, String message, int messageCode) {
        JOptionPane.showMessageDialog(this, message, messageType, messageCode);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicketRegistrationForm::new);
    }
}