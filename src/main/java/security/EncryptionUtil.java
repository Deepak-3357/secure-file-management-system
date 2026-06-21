package main.java.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;

public class EncryptionUtil {

    private static final String ALGO = "AES";

    // 🔑 Generate key from password
    public static SecretKey getKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes());
        return new SecretKeySpec(key, ALGO);
    }

    // 🔒 Encrypt file
    public static void encryptFile(String inputPath, String outputPath, String password) {
        try {
            SecretKey key = getKey(password);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(inputPath);
            FileOutputStream fos = new FileOutputStream(outputPath);

            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] buffer = new byte[1024];
            int read;

            while ((read = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, read);
            }

            fis.close();
            cos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔓 Decrypt file
    public static void decryptFile(String inputPath, String outputPath, String password) {
        try {
            SecretKey key = getKey(password);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(inputPath);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            FileOutputStream fos = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;

            while ((read = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }

            cis.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}