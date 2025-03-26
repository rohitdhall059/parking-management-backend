package com.example.parking.model.payment;

public class CreditCard implements PaymentMethod {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;

    public CreditCard(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = 0.0;
    }

    public CreditCard(String cardNumber2, String credential) {
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean processPayment(double amount) {
        this.amount = amount;
        // In a real implementation, this would connect to a payment processor
        return true;
    }

    @Override
    public String getType() {
        return "CREDIT_CARD";
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String getCredential() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getAmount() {
        return amount;
    }
} 