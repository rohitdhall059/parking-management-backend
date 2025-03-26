package com.example.parking.model.payment;

import com.example.parking.observer.PaymentObserver;
import java.util.ArrayList;
import java.util.List;

// Template Method Pattern: Defines the skeleton of payment processing
public abstract class AbstractPayment implements PaymentMethod {
    protected List<PaymentObserver> observers;
    protected double balance;

    public AbstractPayment() {
        this.observers = new ArrayList<>();
        this.balance = 0.0;
    }

    @Override
    public void processPayment(double amount) {
        if (validatePayment() && processSpecificPayment(amount)) {
            balance += amount;
            notifyObservers("Payment processed: $" + amount);
        }
    }

    @Override
    public void processRefund(double amount) {
        if (validatePayment() && processSpecificRefund(amount)) {
            balance -= amount;
            notifyObservers("Refund processed: $" + amount);
        }
    }

    // Template methods to be implemented by specific payment types
    protected abstract boolean processSpecificPayment(double amount);
    protected abstract boolean processSpecificRefund(double amount);

    @Override
    public void addObserver(PaymentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(PaymentObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (PaymentObserver observer : observers) {
            observer.update(message);
        }
    }
} 