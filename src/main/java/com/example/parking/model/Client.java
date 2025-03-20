package com.example.parking.model;

/**
 * Base client class, extended by Student, FacultyMember, NonFacultyStaff, and Visitor.
 */
public class Client {
    private String clientId;
    private String name;
    private String email;
    private boolean isRegistered;
    private String password; // Add if the UML or your design requires a password field

    /**
     * Basic constructor for a Client.
     * @param clientId  Unique ID for the client
     * @param name      The client's name
     * @param email     The client's email address
     * @param password  (Optional) The client's password if needed
     */
    public Client(String clientId, String name, String email, String password) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.isRegistered = false; // default
        this.password = password;  // if needed
    }

    // Alternate constructor if you do NOT want a password
    public Client(String clientId, String name, String email) {
        this(clientId, name, email, null);
    }

    // Getters and Setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isRegistered=" + isRegistered +
                ", password=" + (password != null ? "******" : "null") +
                '}';
    }
}