package com.example.parking.model.payment;

public interface PaymentMethod {
    boolean processPayment(double amount);
    String getType();
    String getCardNumber();
    String getCredential();
} 