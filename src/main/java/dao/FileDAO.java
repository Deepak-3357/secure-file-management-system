package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.FileModel;
import main.java.util.DBConnection;

public class FileDAO {

    // 🔹 SAVE FILE + RETURN ID
    public static int saveFile(String name, String path, String hash, String owner) {

        int fileId = -1;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO files (filename, path, hash, owner) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);
            ps.setString(2, path);
            ps.setString(3, hash);
            ps.setString(4, owner);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                fileId = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("❌ Error in saveFile()");
            e.printStackTrace();
        }

        return fileId;
    }

    // 🔹 GET ALL FILES
    public static List<FileModel> getAllFiles() {

        List<FileModel> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM files";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new FileModel(
                        rs.getInt("id"),
                        rs.getString("filename"),
                        rs.getString("path"),
                        rs.getString("hash"),
                        rs.getString("owner")   // 🔥 IMPORTANT
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ Error in getAllFiles()");
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 GET FILE BY ID
    public static FileModel getFileById(int id) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM files WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new FileModel(
                        rs.getInt("id"),
                        rs.getString("filename"),
                        rs.getString("path"),
                        rs.getString("hash"),
                        rs.getString("owner")   // 🔥 IMPORTANT
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error in getFileById()");
            e.printStackTrace();
        }

        return null;
    }
    public static void updateHash(int fileId, String newHash) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE files SET hash=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newHash);
            ps.setInt(2, fileId);

            ps.executeUpdate();

            System.out.println("🔄 Hash updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}