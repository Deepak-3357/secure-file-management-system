package main.java.ui;

import javax.swing.*;
import java.awt.*;

import main.java.dao.UserDAO;

public class RegisterUI extends JFrame {
    private static final long serialVersionUID = 1L;

    public RegisterUI() {

        setTitle("Register");
        setSize(450, 450); // 🔥 bigger window
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,18));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 TITLE
        JLabel title = new JLabel("Create Account");
        title.setForeground(new Color(0,170,255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // 🔹 USERNAME
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField userField = new JTextField();
        styleField(userField);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        // 🔹 PASSWORD
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPasswordField passField = new JPasswordField();
        styleField(passField);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passField, gbc);

        // 🔹 ROLE
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        String[] roles = {"USER", "ADMIN"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBackground(new Color(40,40,40));
        roleBox.setForeground(Color.WHITE);
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        // 🔹 BUTTON
        JButton registerBtn = createButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        add(panel);

        // 🔥 ACTION
        registerBtn.addActionListener(e -> {

            String user = userField.getText();
            String pass = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all fields");
                return;
            }

            boolean success = UserDAO.register(user, pass, role);

            if (success) {
                JOptionPane.showMessageDialog(null, "Registered Successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists");
            }
        });

        setVisible(true);
    }

    // 💎 FIELD STYLE
    private void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(200, 30)); // 🔥 bigger field
        field.setBackground(new Color(40,40,40));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        field.setBorder(BorderFactory.createLineBorder(new Color(0,120,215),1));

        // 🔥 FOCUS EFFECT
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(0,170,255),2));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(0,120,215),1));
            }
        });
    }

    // 💎 BUTTON STYLE
    private JButton createButton(String text) {

        JButton btn = new JButton(text);

        btn.setBackground(new Color(0,120,215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(12,10,12,10));

        // 🔥 HOVER + CLICK EFFECT
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0,150,255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0,120,215));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(0,100,180));
            }
            public void mouseReleased(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(0,150,255));
            }
        });

        return btn;
    }
}