package com.example.parking.model.payment;

public class MobilePayment implements PaymentMethod {
    private String phoneNumber;
    private double amount;

    public MobilePayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.amount = 0.0;
    }

    @Override
    public boolean processPayment(double amount) {
        this.amount = amount;
        // In a real implementation, this would connect to a mobile payment processor
        return true;
    }

    @Override
    public String getType() {
        return "MOBILE_PAYMENT";
    }

    @Override
    public String getCardNumber() {
        return phoneNumber;
    }

    @Override
    public String getCredential() {
        return "N/A"; // Mobile payments typically use biometric or app-based authentication
    }

    public double getAmount() {
        return amount;
    }
} 