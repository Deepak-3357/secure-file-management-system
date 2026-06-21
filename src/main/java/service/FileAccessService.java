package main.java.service;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileAccessService {

    public static void openFileWithPermission(String path, String permission) {

        try {
            File original = new File(path);

            // 🔥 VIEW → read-only temp copy
            if (permission.equals("VIEW")) {

                File tempDir = new File("temp");
                if (!tempDir.exists()) tempDir.mkdir();

                String tempName = System.currentTimeMillis() + "_" + original.getName();
                File tempFile = new File(tempDir, tempName);

                Files.copy(original.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // 🔐 Read-only
                tempFile.setWritable(false);
                tempFile.setReadable(true);

                tempFile.deleteOnExit();

                Desktop.getDesktop().open(tempFile);

                System.out.println("Opened in VIEW mode (read-only)");
            }

            // 🔥 DOWNLOAD → give user a copy
            else if (permission.equals("DOWNLOAD")) {

                File downloadDir = new File("downloads");
                if (!downloadDir.exists()) downloadDir.mkdir();

                String fileName = System.currentTimeMillis() + "_" + original.getName();
                File downloadFile = new File(downloadDir, fileName);

                Files.copy(original.toPath(), downloadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Desktop.getDesktop().open(downloadFile);

                System.out.println("File downloaded to /downloads");
            }

            // 🔥 EDIT → open file (editable)
            else if (permission.equals("EDIT")) {

                Desktop.getDesktop().open(original);

                System.out.println("Opened in EDIT mode");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}