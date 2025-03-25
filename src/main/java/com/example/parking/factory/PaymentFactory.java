package com.example.parking.factory;

import com.example.parking.model.*;

public class PaymentFactory {
    public static PaymentMethod createPaymentMethod(String type, double amount, String cardNumber, String credential) {
        switch (type.toLowerCase()) {
            case "credit":
                return new CreditCard(credential, cardNumber, credential, amount);
            case "debit":
                return new DebitCard(cardNumber, credential, credential, amount);
            case "mobile":
                return new MobilePayment(cardNumber, amount);
            default:
                throw new IllegalArgumentException("Unknown payment method type: " + type);
        }
    }
} 