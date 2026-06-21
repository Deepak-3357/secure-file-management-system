package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

import main.java.dao.FileDAO;
import main.java.dao.AccessDAO;
import main.java.dao.LogDAO;
import main.java.model.FileModel;
import main.java.security.HashUtil;
import main.java.service.FileAccessService;

public class FileListUI extends JFrame {
	private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;
    private String username;

    public FileListUI(String username) {

        this.username = username;

        setTitle("My Files");
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 🔥 NON-EDITABLE MODEL
        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Filename");
        model.addColumn("Access");

        table = new JTable(model);
        styleTable(table);

        // 🔥 CENTER ACCESS COLUMN
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                setHorizontalAlignment(CENTER);
                setText(value.toString());
            }
        });

        JScrollPane pane = new JScrollPane(table);
        panel.add(pane, BorderLayout.CENTER);

        JButton openBtn = new JButton("Open File");
        styleButton(openBtn);

        JPanel bottom = new JPanel();
        bottom.add(openBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        loadFiles();

        openBtn.addActionListener(e -> openFile());

        setVisible(true);
    }

    private void loadFiles() {

        model.setRowCount(0);

        List<FileModel> files = AccessDAO.getAccessibleFiles(username);

        for (FileModel f : files) {

            String permission = AccessDAO.getPermission(username, f.getId());

            String display;

            if (permission.equals("VIEW")) display = "V";
            else if (permission.equals("DOWNLOAD")) display = "\u2193";
            else display = "E";

            model.addRow(new Object[]{
                f.getId(),
                f.getFilename(),
                display
            });
        }
    }

    private void openFile() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a file");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        FileModel file = FileDAO.getFileById(id);
        String permission = AccessDAO.getPermission(username, id);

        try {
            String currentHash = HashUtil.generateHash(file.getPath());

            if (!currentHash.equals(file.getHash())) {
                JOptionPane.showMessageDialog(null, "⚠️ File Tampered!");
                return;
            }

            FileAccessService.openFileWithPermission(file.getPath(), permission);

            LogDAO.logAction(username, "Opened file: " + file.getFilename());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleTable(JTable t) {
        t.setRowHeight(30);
        t.getTableHeader().setBackground(new Color(50,150,250));
        t.getTableHeader().setForeground(Color.WHITE);
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(50,150,250));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
    }
}