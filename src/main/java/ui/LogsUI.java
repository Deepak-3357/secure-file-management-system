package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import main.java.dao.LogDAO;
import main.java.model.LogModel;

public class LogsUI extends JFrame {
	private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    public LogsUI() {

        setTitle("System Logs");
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 🔥 NON-EDITABLE TABLE
        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        model.addColumn("User");
        model.addColumn("Action");
        model.addColumn("Time");

        table = new JTable(model);

        styleTable(table);

        JScrollPane pane = new JScrollPane(table);
        panel.add(pane, BorderLayout.CENTER);

        add(panel);

        loadLogs();

        setVisible(true);
    }

    // 🔹 LOAD LOGS
    private void loadLogs() {

        model.setRowCount(0);

        List<LogModel> logs = LogDAO.getLogs();

        for (LogModel log : logs) {
            model.addRow(new Object[]{
                log.getUsername(),
                log.getAction(),
                log.getTimestamp()
            });
        }
    }

    // 🔥 TABLE STYLE
    private void styleTable(JTable t) {
        t.setRowHeight(30);
        t.getTableHeader().setBackground(new Color(50,150,250));
        t.getTableHeader().setForeground(Color.WHITE);
    }
}