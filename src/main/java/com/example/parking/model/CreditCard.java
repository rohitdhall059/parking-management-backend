package com.example.parking.model;

public class CreditCard extends PaymentMethod {
    private String cardNumber;
    private String expiry;

    public CreditCard(double amount, String cardNumber, String expiry) {
        super(amount);
        this.cardNumber = cardNumber;
        this.expiry = expiry;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment of $" + amount);
        // logic for real credit card processing
    }
}