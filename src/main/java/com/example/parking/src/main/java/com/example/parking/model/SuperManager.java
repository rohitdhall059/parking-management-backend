package com.example.parking.model;

public class SuperManager {
    private String email;
    private String password;
    private static SuperManager instance;

    private SuperManager() {
        // Private constructor to enforce singleton pattern
    }

    public static SuperManager getInstance() {
        if (instance == null) {
            instance = new SuperManager();
        }
        return instance;
    }

    public void generateManagementAccount() {
        // Logic to generate and assign a management account
        System.out.println("Generating management account for: " + email);
    }

    // Getters and Setters for email & password
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}