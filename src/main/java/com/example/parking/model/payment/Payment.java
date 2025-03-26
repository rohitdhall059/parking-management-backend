package com.example.parking.model.payment;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private double amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime timestamp;

    public Payment(String id, double amount, PaymentMethod paymentMethod) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean process() {
        boolean success = paymentMethod.processPayment(amount);
        if (success) {
            status = PaymentStatus.COMPLETED;
        } else {
            status = PaymentStatus.FAILED;
        }
        return success;
    }
} 