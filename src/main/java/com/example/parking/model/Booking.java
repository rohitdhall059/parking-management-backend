package com.example.parking.model;

import java.time.LocalDateTime;  // or java.util.Date

public class Booking {
    private String bookingId;
    private String clientId; // or hold a Client reference
    private String spaceId;  // or hold a ParkingSpace reference
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;

    public Booking(String bookingId, String clientId, String spaceId, LocalDateTime startTime) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.spaceId = spaceId;
        this.startTime = startTime;
    }

    // Getters/Setters
    // Example:
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalCost=" + totalCost +
                '}';
    }
}