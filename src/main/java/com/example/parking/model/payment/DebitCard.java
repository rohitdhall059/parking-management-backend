package com.example.parking.model.payment;

public class DebitCard implements PaymentMethod {
    private double amount;
    private String cardNumber;
    private String credential;

    public DebitCard(double amount, String cardNumber, String credential) {
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.credential = credential;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate debit card payment processing
        System.out.println("Processing debit card payment of $" + amount);
        return true;
    }

    @Override
    public String getType() {
        return "DEBIT";
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String getCredential() {
        return credential;
    }
} 