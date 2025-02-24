/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.tickets;

import com.hannsoftware.itms.app.api.TicketServices;
import com.hannsoftware.itms.app.domain.CommentsDTO;
import com.hannsoftware.itms.app.domain.TicketDTO;
import com.hannsoftware.itms.app.form.main.MainForm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TrackTicketsForm extends JFrame {
    private JComboBox<String> statusComboBox;
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;

    public TrackTicketsForm() {
        setTitle("Track Tickets");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new net.miginfocom.swing.MigLayout("fill"));

        panel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"New", "In Progress", "Resolved"});
        panel.add(statusComboBox, "wrap, growx");

        searchButton = new JButton("Search");
        panel.add(searchButton, "span, wrap");

        String[] columnNames = {"Ticket ID", "Title", "Description", "Creation Date", "Priority", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        panel.add(scrollPane, "span, grow, push");

        searchButton.addActionListener(e -> searchTickets());

        add(panel);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainForm();
                dispose();
            }
        });
        
        ticketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = ticketTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Long ticketId = (Long) tableModel.getValueAt(selectedRow, 0);
                        showCommentsPopup(ticketId);
                    }
                }
            }
        });
        
        setVisible(true);
    }

    private void searchTickets() {
        String status = (String) statusComboBox.getSelectedItem();
        List<TicketDTO> tickets = TicketServices.getTicketsByStatus(status);

        tableModel.setRowCount(0);

        for (TicketDTO ticket : tickets) {
            tableModel.addRow(new Object[]{
                    ticket.getId(),
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getCreationDate(),
                    ticket.getPriority(),
                    ticket.getStatus()
            });
        }
        tableModel.fireTableDataChanged();
    }
    
    private void showCommentsPopup(Long ticketId) {
        List<CommentsDTO> comments = TicketServices.getCommentsByTicketId(ticketId);

        if (comments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No comments found.", "Comments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder commentText = new StringBuilder("<html><b>Comments:</b><br>");
        for (CommentsDTO comment : comments) {
            commentText.append("<b>").append(comment.getUser().getFullName()).append(":</b> ")
                       .append(comment.getComment()).append("<br>");
        }
        commentText.append("</html>");

        JOptionPane.showMessageDialog(this, new JLabel(commentText.toString()), "Comments of Ticket - " + ticketId, JOptionPane.INFORMATION_MESSAGE);
    }
}

