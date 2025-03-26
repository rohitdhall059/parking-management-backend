package com.example.parking.factory;

import com.example.parking.model.payment.CreditCard;
import com.example.parking.model.payment.DebitCard;
import com.example.parking.model.payment.MobilePayment;
import com.example.parking.model.payment.PaymentMethod;

public class PaymentFactory {
    public static PaymentMethod createPaymentMethod(String type, String cardNumber, String credential) {
        switch (type.toUpperCase()) {
            case "CREDIT_CARD":
                return new CreditCard(cardNumber, credential);
            case "DEBIT_CARD":
                return new DebitCard(cardNumber, credential);
            case "MOBILE_PAYMENT":
                return new MobilePayment(cardNumber);
            default:
                throw new IllegalArgumentException("Unsupported payment method type: " + type);
        }
    }
} 