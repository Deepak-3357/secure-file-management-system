package main.java.dao;

import java.sql.*;

import main.java.model.User;
import main.java.util.DBConnection;
import main.java.security.HashUtil;   // 🔥 IMPORTANT

public class UserDAO {

    // 🔐 LOGIN (HASH COMPARE)
    public User login(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();

            String hashedPassword = HashUtil.hashPassword(password); // 🔥 HASH

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, hashedPassword); // 🔥 compare hash

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔐 REGISTER (STORE HASH)
    public static boolean register(String username, String password, String role) {

        try {
            Connection con = DBConnection.getConnection();

            String hashedPassword = HashUtil.hashPassword(password); // 🔥 HASH

            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, hashedPassword); // 🔥 store hash
            ps.setString(3, role);

            ps.executeUpdate();

            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // duplicate

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}