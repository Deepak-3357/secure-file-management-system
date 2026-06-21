package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.util.DBConnection;
import main.java.model.LogModel;

public class LogDAO {

    // 🔹 INSERT LOG
    public static void logAction(String user, String action) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO logs (user, action) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user);
            ps.setString(2, action);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL LOGS
    public static List<LogModel> getLogs() {

        List<LogModel> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM logs ORDER BY id DESC";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new LogModel(
                        rs.getString("user"),
                        rs.getString("action"),
                        rs.getString("timestamp")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}