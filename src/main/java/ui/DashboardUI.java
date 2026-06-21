package main.java.ui;

import javax.swing.*;
import java.awt.*;

import main.java.dao.RequestDAO;
import main.java.service.FileService;

public class DashboardUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private String username;
    private String role;
    private JButton requestBtn;

    public DashboardUI(String username, String role) {

        this.username = username;
        this.role = role;

        setTitle("Dashboard");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 25));

        // 🔹 HEADER
        JLabel welcome = new JLabel("Welcome " + username + " (" + role + ")");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        panel.add(welcome, BorderLayout.NORTH);

        // 🔹 GRID
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        grid.setBackground(new Color(25, 25, 25));

        JButton uploadBtn = createCard("Upload");
        JButton viewBtn = createCard("My Files");
        JButton browseBtn = createCard("Browse");

        int pending = RequestDAO.getPendingCount(username);
        requestBtn = createCard("Requests (" + pending + ")");

        JButton adminBtn = createCard("Admin");
        JButton logsBtn = createCard("Logs");
        JButton logoutBtn = createCard("Logout");

        // 🔹 USER
        if (!role.equalsIgnoreCase("ADMIN")) {
            grid.add(uploadBtn);
            grid.add(viewBtn);
            grid.add(browseBtn);
            grid.add(new JLabel());
        }

        // 🔹 ADMIN
        else {
            grid.setLayout(new GridLayout(3, 2, 20, 20));

            grid.add(uploadBtn);
            grid.add(viewBtn);
            grid.add(browseBtn);
            grid.add(requestBtn);
            grid.add(adminBtn);
            grid.add(logsBtn);
        }

        panel.add(grid, BorderLayout.CENTER);

        // 🔹 FOOTER
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(25, 25, 25));

        logoutBtn.setPreferredSize(new Dimension(160, 45));
        bottom.add(logoutBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        // 🔹 ACTIONS
        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                FileService.uploadFile(filePath, username);
                JOptionPane.showMessageDialog(null, "File Uploaded");
            }
        });

        viewBtn.addActionListener(e -> new FileListUI(username));
        browseBtn.addActionListener(e -> new BrowseFilesUI(username));

        requestBtn.addActionListener(e -> new RequestApprovalUI(username, this));

        if (role.equalsIgnoreCase("ADMIN")) {
            adminBtn.addActionListener(e -> new AdminDashboardUI());
            logsBtn.addActionListener(e -> new LogsUI());
        }

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        setVisible(true);
    }

    // 💎 PRO CARD DESIGN
    private JButton createCard(String text) {

        JButton btn = new JButton(text) {

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // 🔥 DEFAULT GRADIENT
                Color top = new Color(0, 120, 215);
                Color bottom = new Color(0, 180, 255);

                if (getModel().isRollover()) {
                    // 🔥 HOVER GRADIENT (brighter)
                    top = new Color(0, 150, 255);
                    bottom = new Color(0, 210, 255);
                }

                GradientPaint gp = new GradientPaint(
                        0, 0, top,
                        0, getHeight(), bottom
                );

                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // 🔥 GLOW EFFECT ON HOVER
                if (getModel().isRollover()) {
                    g2.setColor(new Color(0, 200, 255, 80));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
                }

                super.paintComponent(g);
            }
        };

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 🔥 NO MORE BORDER JUMP
        btn.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        // 🔥 ENABLE SMOOTH ROLLOVER
        btn.setRolloverEnabled(true);

        return btn;
    }

    // 🔔 REFRESH
    public void refreshNotification() {
        int pending = RequestDAO.getPendingCount(username);
        requestBtn.setText("Requests (" + pending + ")");
    }
}