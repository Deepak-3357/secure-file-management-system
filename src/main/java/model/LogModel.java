package main.java.model;

public class LogModel {

    private String username;
    private String action;
    private String timestamp;

    public LogModel(String username, String action, String timestamp) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public String getAction() { return action; }
    public String getTimestamp() { return timestamp; }
}