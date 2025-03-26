package com.example.parking.model.payment;

public class DebitCard implements PaymentMethod {
    private String cardNumber;
    private String pin;
    private double amount;

    public DebitCard(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.amount = 0.0;
    }

    @Override
    public boolean processPayment(double amount) {
        this.amount = amount;
        // In a real implementation, this would connect to a payment processor
        return true;
    }

    @Override
    public String getType() {
        return "DEBIT_CARD";
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String getCredential() {
        return pin;
    }

    public double getAmount() {
        return amount;
    }
} 