package com.example.parking.model;

import com.example.parking.model.client.Client;
import com.example.parking.model.payment.PaymentMethod;
import java.time.LocalDateTime;

public class Booking {
    private final String id;
    private final Client client;
    private final ParkingSpace parkingSpace;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final PaymentMethod paymentMethod;
    private String status;
    private double amount;
    private boolean isRefunded;

    public Booking(String id, Client client, ParkingSpace parkingSpace, LocalDateTime startTime, LocalDateTime endTime, PaymentMethod paymentMethod) {
        this.id = id;
        this.client = client;
        this.parkingSpace = parkingSpace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
        this.amount = 0.0;
        this.isRefunded = false;
    }

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public void setRefunded(boolean refunded) {
        isRefunded = refunded;
    }

    public double calculateRefund() {
        if (isRefunded || endTime.isBefore(LocalDateTime.now())) {
            return 0.0;
        }
        // Simple refund calculation: 50% of the amount
        return amount * 0.5;
    }

    public void processRefund(double refundAmount) {
        if (!isRefunded) {
            this.amount -= refundAmount;
            this.isRefunded = true;
            this.status = "REFUNDED";
        }
    }
} 