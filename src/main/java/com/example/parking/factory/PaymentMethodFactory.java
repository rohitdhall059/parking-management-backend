package com.example.parking.factory;

import com.example.parking.model.PaymentMethod;
import com.example.parking.model.CreditCard;
import com.example.parking.model.DebitCard;
import com.example.parking.model.MobilePayment;

public class PaymentMethodFactory {

    /**
     * Creates a PaymentMethod based on the specified type.
     *
     * @param type The type of payment method ("credit", "debit", or "mobile").
     * @param amount The amount to process.
     * @param cardNumber The card number or phone number.
     * @param credential For a credit card, this is the expiry date (in "MM/yy" format);
     *                   for a debit card, this is the PIN;
     *                   for mobile payment, this is the card holder name.
     * @return A PaymentMethod instance.
     * @throws IllegalArgumentException if the type is unrecognized.
     */
    public static PaymentMethod createPaymentMethod(String type, double amount, String cardNumber, String credential) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Payment method type must be specified.");
        }
        
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