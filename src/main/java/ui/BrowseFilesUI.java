package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.List;

import main.java.dao.FileDAO;
import main.java.dao.AccessDAO;
import main.java.dao.RequestDAO;
import main.java.dao.LogDAO;
import main.java.model.FileModel;
import main.java.security.HashUtil;
import main.java.security.EncryptionUtil;
import main.java.service.FileAccessService;

public class BrowseFilesUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;
    private String username;

    public BrowseFilesUI(String username) {

        this.username = username;

        setTitle("Browse Files");
        setSize(750, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Filename");
        model.addColumn("Owner");
        model.addColumn("Access");

        table = new JTable(model);
        styleTable(table);

        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                setHorizontalAlignment(CENTER);
                setText(value.toString());
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        JButton actionBtn = new JButton("Open / Request");
        styleButton(actionBtn);
        bottom.add(actionBtn);

        panel.add(bottom, BorderLayout.SOUTH);
        add(panel);

        loadFiles();

        actionBtn.addActionListener(e -> handleAction());

        setVisible(true);
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

    private void loadFiles() {

        model.setRowCount(0);

        List<FileModel> files = FileDAO.getAllFiles();

        for (FileModel f : files) {

            String permission = AccessDAO.getPermission(username, f.getId());

            if (username.equalsIgnoreCase("admin")) {
                permission = "EDIT";
            }

            String accessDisplay;

            if (permission == null) accessDisplay = "X";
            else if (permission.equals("VIEW")) accessDisplay = "V";
            else if (permission.equals("DOWNLOAD")) accessDisplay = "D";
            else accessDisplay = "E";

            model.addRow(new Object[]{
                f.getId(),
                f.getFilename(),
                f.getOwner(),
                accessDisplay
            });
        }
    }

    private void handleAction() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a file");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String permission = AccessDAO.getPermission(username, id);

        if (username.equalsIgnoreCase("admin")) {
            permission = "EDIT";
        }

        // ❌ NO ACCESS → REQUEST
        if (permission == null) {

            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "No access. Request permission?",
                    "Request Access",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                boolean success = RequestDAO.requestAccess(username, id);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Request Sent ✅");
                } else {
                    JOptionPane.showMessageDialog(null, "Request already exists ⚠️");
                }
            }

            return;
        }

        // 🔓 HAS ACCESS
        FileModel file = FileDAO.getFileById(id);

        try {

            // 🔓 Step 1: Decrypt
            String tempPath = "files/temp_" + file.getFilename();

            EncryptionUtil.decryptFile(
                    file.getPath(),
                    tempPath,
                    "secret123"
            );

            // 🔐 Step 2: Tamper check (WARNING ONLY)
            if (!permission.equals("EDIT")) {

                String currentHash = HashUtil.generateHash(file.getPath());

                if (!currentHash.equals(file.getHash())) {

                    LogDAO.logAction(username, "TAMPER DETECTED: " + file.getFilename());

                    JOptionPane.showMessageDialog(
                            null,
                            "⚠️ File has been modified (tampered)",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );

                    // ❌ NO RETURN → allow open
                }
            }

            // 🔓 Step 3: Open file
            FileAccessService.openFileWithPermission(tempPath, permission);

            // ✏️ Step 4: EDIT → re-encrypt + update hash
            if (permission.equals("EDIT")) {

                EncryptionUtil.encryptFile(tempPath, file.getPath(), "secret123");

                String newHash = HashUtil.generateHash(file.getPath());
                FileDAO.updateHash(file.getId(), newHash);

                JOptionPane.showMessageDialog(null, "Edited & Secured ✅");
            }

            // 🧹 Step 5: Cleanup
            new File(tempPath).delete();

            LogDAO.logAction(username, "Opened file: " + file.getFilename());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}