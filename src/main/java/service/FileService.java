package main.java.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import main.java.security.HashUtil;
import main.java.security.EncryptionUtil; // 🔥 NEW
import main.java.dao.FileDAO;
import main.java.dao.LogDAO;
import main.java.dao.AccessDAO;

public class FileService {

    public static void uploadFile(String sourcePath, String username) {

        try {
            System.out.println("Selected file: " + sourcePath);

            // 🔹 Step 1: Check source file
            File source = new File(sourcePath);

            if (!source.exists()) {
                System.out.println("❌ File does not exist");
                return;
            }

            // 🔹 Step 2: Create folder
            File folder = new File("files");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // 🔹 Step 3: Temp copy (original)
            File tempCopy = new File(folder, "temp_" + source.getName());
            Files.copy(source.toPath(), tempCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 🔥 Step 4: Encrypt file
            String encryptedPath = "files/enc_" + source.getName();

            EncryptionUtil.encryptFile(
                    tempCopy.getAbsolutePath(),
                    encryptedPath,
                    "secret123"   // 🔑 password (same everywhere for now)
            );

            System.out.println("🔐 File encrypted: " + encryptedPath);

            // 🔹 Step 5: Delete temp original
            tempCopy.delete();

            // 🔹 Step 6: Generate hash (ON ENCRYPTED FILE)
            String hash = HashUtil.generateHash(encryptedPath);
            System.out.println("🔐 Hash: " + hash);

            if (hash == null) {
                System.out.println("❌ Hash failed");
                return;
            }

            // 🔹 Step 7: Save in DB
            int fileId = FileDAO.saveFile(
                    source.getName(),
                    encryptedPath,
                    hash,
                    username
            );

            System.out.println("Generated File ID: " + fileId);

            // 🔥 Step 8: Give owner full access
            AccessDAO.grantAccess(username, fileId, "EDIT");

            // 🔥 Step 9: Log
            LogDAO.logAction(username, "Uploaded (encrypted) file: " + source.getName());

            System.out.println("💾 Stored securely in DB");

        } catch (Exception e) {
            System.out.println("❌ Upload error:");
            e.printStackTrace();
        }
    }
}