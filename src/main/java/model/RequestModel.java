package main.java.model;

public class RequestModel {

    private int id;
    private String username;
    private int fileId;

    public RequestModel(int id, String username, int fileId) {
        this.id = id;
        this.username = username;
        this.fileId = fileId;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getFileId() { return fileId; }
}