package com.example.parking.model;

import java.time.LocalDateTime;

public class Booking {
    private String bookingId;
    private String clientId;
    private String spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private String status;

    public Booking(String bookingId, String clientId, String spaceId, String status) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.spaceId = spaceId;
        this.startTime = LocalDateTime.now();
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 