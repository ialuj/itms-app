/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.form.tickets;

import com.hannsoftware.itms.app.api.TicketServices;
import com.hannsoftware.itms.app.domain.TicketDTO;
import com.hannsoftware.itms.app.form.main.MainForm;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SolveTicketsForm extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;

    public SolveTicketsForm() {
        setTitle("Solve Tickets");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(new Object[]{"Ticket ID", "Title", "Priority", "Status", "Comment", "Update Status"}, 0);
        ticketTable = new JTable(tableModel);

        ticketTable.getColumn("Comment").setCellRenderer(new ButtonRenderer());
        ticketTable.getColumn("Update Status").setCellRenderer(new ButtonRenderer());

        ticketTable.getColumn("Comment").setCellEditor(new ButtonEditor(new JCheckBox(), "Comment"));
        ticketTable.getColumn("Update Status").setCellEditor(new ButtonEditor(new JCheckBox(), "Update Status"));

        add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        loadTickets();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainForm();
                dispose();
            }
        });
        setVisible(true);
    }

    private void loadTickets() {
        List<TicketDTO> tickets = TicketServices.getAllUnsolvedTickets();

        tableModel.setRowCount(0);
        for (TicketDTO ticket : tickets) {
            tableModel.addRow(new Object[]{ticket.getId(), ticket.getTitle(), ticket.getPriority(), ticket.getStatus(), "Comment", "Update Status"});
        }
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, String label) {
            super(checkBox);
            this.label = label;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            selectedRow = row;
            button.setText((value == null) ? "" : value.toString());
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                Long ticketId = (Long) tableModel.getValueAt(selectedRow, 0);

                if ("Comment".equals(label)) {
                    openCommentDialog(ticketId);
                } else if ("Update Status".equals(label)) {
                    openUpdateStatusDialog(ticketId);
                }
            }
            isPushed = false;
            return label;
        }
    }

    private void openCommentDialog(Long ticketId) {
        JDialog dialog = new JDialog(this, "Add Comment", true);
        dialog.setLayout(new BorderLayout());

        JTextArea commentArea = new JTextArea(5, 20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));

        JPanel buttons = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            String comment = commentArea.getText().trim();
            if (!comment.isEmpty()) {
                TicketServices.addComment(ticketId, comment);
                JOptionPane.showMessageDialog(this, "Comment added successfully!");
            }
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttons.add(btnSave);
        buttons.add(btnCancel);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openUpdateStatusDialog(Long ticketId) {
        JDialog dialog = new JDialog(this, "Update Ticket Status", true);
        dialog.setLayout(new BorderLayout());

        String[] options = {"In Progress", "Resolved"};
        JComboBox<String> statusBox = new JComboBox<>(options);

        JPanel panel = new JPanel();
        panel.add(new JLabel("New Status:"));
        panel.add(statusBox);

        JPanel buttons = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            String newStatus = (String) statusBox.getSelectedItem();
            if (newStatus != null) {
                TicketServices.updateTicketStatus(ticketId, newStatus);
                JOptionPane.showMessageDialog(this, "Status updated successfully!");
                loadTickets();
            }
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttons.add(btnSave);
        buttons.add(btnCancel);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SolveTicketsForm::new);
    }
}