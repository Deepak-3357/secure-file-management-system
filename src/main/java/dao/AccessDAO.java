package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.FileModel;
import main.java.util.DBConnection;

public class AccessDAO {

    // 🔹 Grant access (INSERT or UPDATE)
    public static void grantAccess(String username, int fileId, String permission) {
        try {
            Connection con = DBConnection.getConnection();

            // 🔥 Check if already exists
            String checkSql = "SELECT * FROM access_control WHERE username=? AND file_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, username);
            checkPs.setInt(2, fileId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // 🔥 Update permission
                String updateSql = "UPDATE access_control SET permission=? WHERE username=? AND file_id=?";
                PreparedStatement ps = con.prepareStatement(updateSql);

                ps.setString(1, permission);
                ps.setString(2, username);
                ps.setInt(3, fileId);

                ps.executeUpdate();
            } else {
                // 🔥 Insert new
                String insertSql = "INSERT INTO access_control (username, file_id, permission) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insertSql);

                ps.setString(1, username);
                ps.setInt(2, fileId);
                ps.setString(3, permission);

                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Check access
    public static boolean hasAccess(String username, int fileId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM access_control WHERE username=? AND file_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, fileId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Get all accessible files for user
    public static List<FileModel> getAccessibleFiles(String username) {

        List<FileModel> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT f.* FROM files f " +
                         "JOIN access_control a ON f.id = a.file_id " +
                         "WHERE a.username = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new FileModel(
                        rs.getInt("id"),
                        rs.getString("filename"),
                        rs.getString("path"),
                        rs.getString("hash"),
                        rs.getString("owner")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Get permission (VIEW / DOWNLOAD / EDIT)
    public static String getPermission(String username, int fileId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT permission FROM access_control WHERE username=? AND file_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, fileId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("permission");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔥 ADMIN: View by FILE
    public static List<String[]> getAccessByFile() {

        List<String[]> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT file_id, username, permission FROM access_control";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                        String.valueOf(rs.getInt("file_id")),
                        rs.getString("username"),
                        rs.getString("permission")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 ADMIN: View by USER
    public static List<String[]> getAccessByUser() {

        List<String[]> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT username, file_id, permission FROM access_control";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("username"),
                        String.valueOf(rs.getInt("file_id")),
                        rs.getString("permission")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Update permission
    public static void updatePermission(String username, int fileId, String permission) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE access_control SET permission=? WHERE username=? AND file_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, permission);
            ps.setString(2, username);
            ps.setInt(3, fileId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Remove access
    public static void removeAccess(String username, int fileId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM access_control WHERE username=? AND file_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, fileId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}