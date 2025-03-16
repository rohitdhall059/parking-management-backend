package com.example.parking.model;

public class DebitCard extends PaymentMethod {
    private String cardNumber;
    private String pin;

    public DebitCard(double amount, String cardNumber, String pin) {
        super(amount);
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing debit card payment of $" + amount);
        // logic for real debit card processing
    }
}