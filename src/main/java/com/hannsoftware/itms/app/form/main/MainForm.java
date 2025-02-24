/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.main;

import com.hannsoftware.itms.app.api.TicketServices;
import com.hannsoftware.itms.app.api.UserServices;
import com.hannsoftware.itms.app.form.auditlog.AuditLogForm;
import com.hannsoftware.itms.app.form.tickets.SolveTicketsForm;
import com.hannsoftware.itms.app.form.tickets.TicketRegistrationForm;
import com.hannsoftware.itms.app.form.tickets.TrackTicketsForm;
import com.hannsoftware.itms.app.form.users.UpdatePasswordForm;
import com.hannsoftware.itms.app.form.users.UserRegistrationForm;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class MainForm extends JFrame {
    private JLabel lblNewTickets;
    private JLabel lblInProgressTickets;
    private JLabel lblSolvedTickets;
    private JLabel lblUnsolvedTickets;

    public MainForm() {
        setTitle("IT Support Ticket Management System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new MigLayout("", "[200px,grow][grow]", "[grow]"));

        JPanel menuPanel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[][][][][][][]"));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        JButton btnRegisterUsers = new JButton("Register Users");
        JButton btnRegisterTickets = new JButton("Register Tickets");
        JButton btnTrackTickets = new JButton("Track Tickets");
        JButton btnSolveTickets = new JButton("Solve Tickets");
        JButton btnAuditLogs = new JButton("Show Audit Logs");
        JButton btnChangePassword = new JButton("Change Password");

        btnRegisterUsers.addActionListener(e -> new UserRegistrationForm());
        btnRegisterTickets.addActionListener(e -> { 
            new TicketRegistrationForm();
            dispose();
                });
        btnTrackTickets.addActionListener(e -> { 
            new TrackTicketsForm();
            dispose();
                });
        btnSolveTickets.addActionListener(e -> {
            new SolveTicketsForm();
            dispose();
        });
        btnAuditLogs.addActionListener(e -> {
            new AuditLogForm();
            dispose();
        });
        btnChangePassword.addActionListener(e -> {
           new UpdatePasswordForm(); 
           dispose();
        });

        if("Employee".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
            menuPanel.add(btnRegisterTickets);
            menuPanel.add(btnTrackTickets);
            menuPanel.add(btnChangePassword);
        } else if("IT Support".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
            menuPanel.add(btnRegisterUsers);
            menuPanel.add(btnSolveTickets);
            menuPanel.add(btnAuditLogs);
            menuPanel.add(btnChangePassword);
        } else {
            showDialogMessage("Error", "Problems accessing the System, please contact the Administrator!", JOptionPane.OK_OPTION);
            dispose();
        }
        
        JPanel statsPanel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[][][][]"));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Ticket Statistics"));

        lblNewTickets = new JLabel("New Tickets:  " + TicketServices.getNumberOfTicketsByStatus("New"));
        lblInProgressTickets = new JLabel("In Progress:  " + TicketServices.getNumberOfTicketsByStatus("In Progress"));
        lblSolvedTickets = new JLabel("Solved:  " + TicketServices.getNumberOfTicketsByStatus("Resolved"));
        lblUnsolvedTickets = new JLabel("Unsolved:  " + TicketServices.getNumberOfUnsolvedTickets());

        statsPanel.add(lblNewTickets);
        statsPanel.add(lblInProgressTickets);
        statsPanel.add(lblSolvedTickets);
        statsPanel.add(lblUnsolvedTickets);

        add(menuPanel, "cell 0 0, grow");
        add(statsPanel, "cell 1 0, grow");

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainForm::new);
    }
    
    private void showDialogMessage(String messageType, String message, int messageCode) {
        JOptionPane.showMessageDialog(this, message, messageType, messageCode);
    }
}
