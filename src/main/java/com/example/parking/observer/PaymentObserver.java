package com.example.parking.observer;

// Observer Pattern: Interface for objects that want to be notified of payment events
public interface PaymentObserver {
    void update(String message);
} 