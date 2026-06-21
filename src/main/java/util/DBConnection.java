package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/capstone",
                ""YOUR_USERNAME",
                "YOUR_PASSWORD"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}