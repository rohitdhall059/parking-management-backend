package com.example.parking.model.payment;

public class MobilePayment implements PaymentMethod {
    private double amount;
    private String cardNumber;
    private String credential;

    public MobilePayment(double amount, String cardNumber, String credential) {
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.credential = credential;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate mobile payment processing
        System.out.println("Processing mobile payment of $" + amount);
        return true;
    }

    @Override
    public String getType() {
        return "MOBILE";
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