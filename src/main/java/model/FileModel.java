package main.java.model;

public class FileModel {

    // 🔹 Fields
    private int id;
    private String filename;
    private String path;
    private String hash;
    private String owner;

    // 🔹 Constructor
    public FileModel(int id, String filename, String path, String hash, String owner) {
        this.id = id;
        this.filename = filename;
        this.path = path;
        this.hash = hash;
        this.owner = owner;
    }

    // 🔹 Getters
    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public String getHash() {
        return hash;
    }

    public String getOwner() {
        return owner;
    }

    // 🔹 Optional (for debugging / logging)
    @Override
    public String toString() {
        return "FileModel{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}