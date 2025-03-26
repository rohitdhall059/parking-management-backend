package com.example.parking.model;

public class SuperManager {
    private static SuperManager instance;
    private String username;
    private String password;
    private boolean isLoggedIn;

    // Private constructor to prevent external instantiation
    private SuperManager() {
        this.username = "supermanager";
        this.password = "admin123"; // In production, use proper password hashing
        this.isLoggedIn = false;
    }

    // Public static method to get the instance
    public static SuperManager getInstance() {
        if (instance == null) {
            instance = new SuperManager();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to generate management accounts (only available to super manager)
    public boolean generateManagementAccount(String username, String password) {
        if (!isLoggedIn) {
            return false;
        }
        // Implementation for generating management accounts
        return true;
    }
}