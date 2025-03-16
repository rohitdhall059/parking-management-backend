package com.example.parking.model;
public class Client {
    private String clientId;
    private String name;
    private String email;
    private boolean isRegistered;

    public Client(String clientId, String name, String email) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.isRegistered = false;
    }

    // Getters and Setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isRegistered() { return isRegistered; }
    public void setRegistered(boolean registered) { isRegistered = registered; }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isRegistered=" + isRegistered +
                '}';
    }
}