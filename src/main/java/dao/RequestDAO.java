package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.RequestModel;
import main.java.util.DBConnection;

public class RequestDAO {

    // 🔥 FIXED → now returns boolean
    public static boolean requestAccess(String username, int fileId) {
        try {
            Connection con = DBConnection.getConnection();

            // 🔍 Check duplicate
            String checkSql = "SELECT * FROM requests WHERE username=? AND file_id=? AND status='PENDING'";
            PreparedStatement checkPs = con.prepareStatement(checkSql);

            checkPs.setString(1, username);
            checkPs.setInt(2, fileId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                return false; // ❌ already exists
            }

            // ✅ Insert request
            String sql = "INSERT INTO requests (username, file_id, status) VALUES (?, ?, 'PENDING')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, fileId);

            ps.executeUpdate();

            return true; // ✅ success

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Get requests for FILE OWNER
    public static List<RequestModel> getRequestsForOwner(String owner) {

        List<RequestModel> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT r.* FROM requests r " +
                         "JOIN files f ON r.file_id = f.id " +
                         "WHERE f.owner = ? AND r.status = 'PENDING'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, owner);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new RequestModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("file_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 ADMIN → Get ALL requests
    public static List<RequestModel> getAllRequests() {

        List<RequestModel> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM requests WHERE status='PENDING'";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new RequestModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("file_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Approve request
    public static void approveRequest(int requestId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE requests SET status='APPROVED' WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Reject request
    public static void rejectRequest(int requestId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE requests SET status='REJECTED' WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 ADMIN + USER COUNT FIX
    public static int getPendingCount(String user) {

        int count = 0;

        try {
            Connection con = DBConnection.getConnection();

            String sql;
            PreparedStatement ps;

            if (user.equalsIgnoreCase("admin")) {
                sql = "SELECT COUNT(*) FROM requests WHERE status='PENDING'";
                ps = con.prepareStatement(sql);
            } else {
                sql = "SELECT COUNT(*) FROM requests r " +
                      "JOIN files f ON r.file_id = f.id " +
                      "WHERE f.owner = ? AND r.status = 'PENDING'";
                ps = con.prepareStatement(sql);
                ps.setString(1, user);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}