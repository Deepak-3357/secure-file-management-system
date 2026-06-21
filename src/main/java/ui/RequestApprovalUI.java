package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import main.java.dao.RequestDAO;
import main.java.dao.AccessDAO;
import main.java.model.RequestModel;

public class RequestApprovalUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;
    private String owner;
    private DashboardUI dashboard;
    private JComboBox<String> permissionBox;

    public RequestApprovalUI(String owner, DashboardUI dashboard) {

        this.owner = owner;
        this.dashboard = dashboard;

        setTitle("Requests");
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("File ID");

        table = new JTable(model);
        styleTable(table);

        JScrollPane pane = new JScrollPane(table);
        panel.add(pane, BorderLayout.CENTER);

        // 🔥 Bottom Panel
        JPanel bottom = new JPanel();

        permissionBox = new JComboBox<>(new String[]{"VIEW","DOWNLOAD","EDIT"});
        bottom.add(permissionBox);

        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");

        styleButton(approve);
        styleButton(reject);

        bottom.add(approve);
        bottom.add(reject);

        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        loadRequests();

        approve.addActionListener(e -> approveRequest());
        reject.addActionListener(e -> rejectRequest());

        setVisible(true);
    }

    // 🔥 FIXED METHOD
    private void loadRequests() {

        model.setRowCount(0);

        List<RequestModel> list;

        // ✅ ADMIN → see all requests
        if (owner.equalsIgnoreCase("admin")) {
            list = RequestDAO.getAllRequests();
        }
        // ✅ NORMAL USER → only their file requests
        else {
            list = RequestDAO.getRequestsForOwner(owner);
        }

        for (RequestModel r : list) {
            model.addRow(new Object[]{
                r.getId(),
                r.getUsername(),
                r.getFileId()
            });
        }
    }

    private void approveRequest() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select request");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String user = (String) model.getValueAt(row, 1);
        int fileId = (int) model.getValueAt(row, 2);

        String perm = (String) permissionBox.getSelectedItem();

        AccessDAO.grantAccess(user, fileId, perm);
        RequestDAO.approveRequest(id);

        JOptionPane.showMessageDialog(null, "Approved");

        dispose();
        dashboard.refreshNotification();
    }

    private void rejectRequest() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select request");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        RequestDAO.rejectRequest(id);

        JOptionPane.showMessageDialog(null, "Rejected");

        dispose();
        dashboard.refreshNotification();
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