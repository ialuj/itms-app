/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.auditlog;

import com.hannsoftware.itms.app.api.AuditLogServices;
import com.hannsoftware.itms.app.domain.AuditLogDTO;
import com.hannsoftware.itms.app.form.main.MainForm;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AuditLogForm extends JFrame {

    private JTable auditLogTable;
    private DefaultTableModel tableModel;

    public AuditLogForm() {
        setTitle("Audit Log");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        String[] columnNames = {"Audit ID", "Action", "Creation Date", "Ticket ID", "User Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        auditLogTable = new JTable(tableModel);

        List<AuditLogDTO> auditLogs = AuditLogServices.getAllAuditLogs();
        populateTable(auditLogs);

        JScrollPane scrollPane = new JScrollPane(auditLogTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainForm();
                dispose();
            }
        });
        
        closeButton.addActionListener(e -> {
            new MainForm();
            dispose();
                });
        
        setVisible(true);
    }

    private void populateTable(List<AuditLogDTO> auditLogs) {
        tableModel.setRowCount(0);

        for (AuditLogDTO log : auditLogs) {
            tableModel.addRow(new Object[]{
                    log.getId(),
                    log.getAction(),
                    log.getCreationDate(),
                    log.getTicket().getId(),
                    log.getUser().getFullName()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AuditLogForm::new);
    }
}
