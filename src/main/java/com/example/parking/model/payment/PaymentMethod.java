package com.example.parking.model.payment;

import com.example.parking.observer.PaymentObserver;
import java.util.ArrayList;
import java.util.List;

// Strategy Pattern: PaymentMethod interface defines the strategy for different payment types
public interface PaymentMethod {
    void processPayment(double amount);
    void processRefund(double amount);
    String getPaymentDetails();
    boolean validatePayment();
    
    // Observer Pattern: Methods to manage observers
    void addObserver(PaymentObserver observer);
    void removeObserver(PaymentObserver observer);
    void notifyObservers(String message);
} 