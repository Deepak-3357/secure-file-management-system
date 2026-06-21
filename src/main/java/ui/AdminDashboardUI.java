package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import main.java.dao.AccessDAO;

public class AdminDashboardUI extends JFrame {
	private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> viewBox;
    private JComboBox<String> permissionBox;

    public AdminDashboardUI() {

        setTitle("Admin Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        // 🔹 View Selector
        viewBox = new JComboBox<>(new String[]{"View by File", "View by User"});
        viewBox.setBounds(20, 20, 200, 30);
        add(viewBox);

        // 🔹 Table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 70, 640, 250);
        add(pane);

        // 🔹 Permission Dropdown
        permissionBox = new JComboBox<>(new String[]{"VIEW", "DOWNLOAD", "EDIT"});
        permissionBox.setBounds(200, 340, 150, 30);
        add(permissionBox);

        // 🔹 Buttons
        JButton updateBtn = new JButton("Update Permission");
        updateBtn.setBounds(50, 400, 180, 30);
        add(updateBtn);

        JButton removeBtn = new JButton("Remove Access");
        removeBtn.setBounds(300, 400, 180, 30);
        add(removeBtn);

        // 🔥 Load default
        loadByFile();

        // 🔹 Change View
        viewBox.addActionListener(e -> {
            if (viewBox.getSelectedIndex() == 0) {
                loadByFile();
            } else {
                loadByUser();
            }
        });

        // 🔹 Update
        updateBtn.addActionListener(e -> updateAccess());

        // 🔹 Remove
        removeBtn.addActionListener(e -> removeAccess());

        setVisible(true);
    }

    // 🔹 LOAD FILE VIEW
    private void loadByFile() {

        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("File ID");
        model.addColumn("Username");
        model.addColumn("Permission");

        List<String[]> list = AccessDAO.getAccessByFile();

        for (String[] row : list) {
            model.addRow(row);
        }
    }

    // 🔹 LOAD USER VIEW
    private void loadByUser() {

        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Username");
        model.addColumn("File ID");
        model.addColumn("Permission");

        List<String[]> list = AccessDAO.getAccessByUser();

        for (String[] row : list) {
            model.addRow(row);
        }
    }

    // 🔹 UPDATE PERMISSION
    private void updateAccess() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a row");
            return;
        }

        String permission = (String) permissionBox.getSelectedItem();

        String username;
        int fileId;

        if (viewBox.getSelectedIndex() == 0) {
            fileId = Integer.parseInt(model.getValueAt(row, 0).toString());
            username = model.getValueAt(row, 1).toString();
        } else {
            username = model.getValueAt(row, 0).toString();
            fileId = Integer.parseInt(model.getValueAt(row, 1).toString());
        }

        AccessDAO.updatePermission(username, fileId, permission);

        JOptionPane.showMessageDialog(null, "Updated ✅");

        reload();
    }

    // 🔹 REMOVE ACCESS
    private void removeAccess() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a row");
            return;
        }

        String username;
        int fileId;

        if (viewBox.getSelectedIndex() == 0) {
            fileId = Integer.parseInt(model.getValueAt(row, 0).toString());
            username = model.getValueAt(row, 1).toString();
        } else {
            username = model.getValueAt(row, 0).toString();
            fileId = Integer.parseInt(model.getValueAt(row, 1).toString());
        }

        AccessDAO.removeAccess(username, fileId);

        JOptionPane.showMessageDialog(null, "Access Removed ❌");

        reload();
    }

    private void reload() {
        if (viewBox.getSelectedIndex() == 0) {
            loadByFile();
        } else {
            loadByUser();
        }
    }
}