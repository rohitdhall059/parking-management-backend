package com.example.parking.model;

public abstract class PaymentMethod {
    protected String paymentID;
    protected double amount;

    public PaymentMethod(double amount) {
        this.amount = amount;
    }

    public abstract void processPayment(double amount);

    public abstract void processRefund(double amount);

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}