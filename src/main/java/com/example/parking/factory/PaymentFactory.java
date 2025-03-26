package com.example.parking.factory;

import com.example.parking.model.payment.CreditCard;
import com.example.parking.model.payment.DebitCard;
import com.example.parking.model.payment.MobilePayment;
import com.example.parking.model.payment.PaymentMethod;

public class PaymentFactory {
    public static PaymentMethod createPaymentMethod(String type, double amount, String cardNumber, String credential) {
        switch (type.toUpperCase()) {
            case "CREDIT":
                return new CreditCard(amount, cardNumber, credential);
            case "DEBIT":
                return new DebitCard(amount, cardNumber, credential);
            case "MOBILE":
                return new MobilePayment(amount, cardNumber, credential);
            default:
                throw new IllegalArgumentException("Unknown payment method type: " + type);
        }
    }
} 