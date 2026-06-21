package main.java.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

import main.java.dao.AccessDAO;

public class GrantAccessUI extends JFrame {
	private static final long serialVersionUID = 1L;

    public GrantAccessUI() {

        setTitle("Grant Access");
        setSize(300, 250);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(120, 30, 120, 25);
        add(userField);

        JLabel fileLabel = new JLabel("File ID:");
        fileLabel.setBounds(30, 80, 100, 25);
        add(fileLabel);

        JTextField fileField = new JTextField();
        fileField.setBounds(120, 80, 120, 25);
        add(fileField);

        JButton grantBtn = new JButton("Grant");
        grantBtn.setBounds(80, 130, 120, 30);
        add(grantBtn);

        grantBtn.addActionListener(e -> {
            String user = userField.getText();
            int fileId = Integer.parseInt(fileField.getText());

            AccessDAO.grantAccess(user, fileId, "EDIT");

            JOptionPane.showMessageDialog(null, "Access Granted ✅");
        });

        setVisible(true);
    }
}