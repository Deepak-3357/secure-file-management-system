package main.java.ui;

import javax.swing.*;
import java.awt.*;

import main.java.dao.UserDAO;
import main.java.model.User;

public class LoginUI extends JFrame {
	private static final long serialVersionUID = 1L;
    public LoginUI() {

        setTitle("Login");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(new Color(20, 20, 20));

        // 🔹 Title
        JLabel title = new JLabel("Secure File System");
        title.setForeground(new Color(0, 150, 255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // 🔹 Labels
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);

        // 🔹 Fields (🔥 FIXED)
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        // 🔥 Field Styling
        userField.setBackground(new Color(40, 40, 40));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);

        passField.setBackground(new Color(40, 40, 40));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);

        // 🔹 Buttons
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn);

        JButton signupBtn = new JButton("Sign Up");
        styleButton(signupBtn);

        // 🔹 Add components
        panel.add(title);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        add(panel);

        // 🔥 LOGIN ACTION
        loginBtn.addActionListener(e -> {

            String user = userField.getText();
            String pass = new String(passField.getPassword());

            UserDAO dao = new UserDAO();
            User u = dao.login(user, pass);

            if (u != null) {
                dispose();
                new DashboardUI(u.getUsername(), u.getRole());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login ❌");
            }
        });

        // 🔹 SIGNUP ACTION
        signupBtn.addActionListener(e -> new RegisterUI());

        setVisible(true);
    }

    // 🔥 BUTTON STYLE
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}