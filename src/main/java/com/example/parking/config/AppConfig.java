package com.example.parking.config;

public class AppConfig {

    // Singleton instance
    private static AppConfig instance;

    // Configuration fields (CSV file paths)
    private String clientsCsvPath;
    private String parkingspacesCsvPath;
    private String bookingsCsvPath;

    // Private constructor prevents instantiation from outside
    private AppConfig() {
        // Set default CSV paths (adjust these paths as needed)
        this.clientsCsvPath = "data/clients.csv";
        this.parkingspacesCsvPath = "data/parkingspaces.csv";
        this.bookingsCsvPath = "data/bookings.csv";
    }

    /**
     * Returns the singleton instance of AppConfig.
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    // Getters and setters for the configuration fields

    public String getClientsCsvPath() {
        return clientsCsvPath;
    }

    public void setClientsCsvPath(String clientsCsvPath) {
        this.clientsCsvPath = clientsCsvPath;
    }

    public String getParkingspacesCsvPath() {
        return parkingspacesCsvPath;
    }

    public void setParkingspacesCsvPath(String parkingspacesCsvPath) {
        this.parkingspacesCsvPath = parkingspacesCsvPath;
    }

    public String getBookingsCsvPath() {
        return bookingsCsvPath;
    }

    public void setBookingsCsvPath(String bookingsCsvPath) {
        this.bookingsCsvPath = bookingsCsvPath;
    }
}