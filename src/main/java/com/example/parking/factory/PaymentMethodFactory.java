package com.example.parking.factory;

import com.example.parking.model.PaymentMethod;
import com.example.parking.model.CreditCard;
import com.example.parking.model.DebitCard;

public class PaymentMethodFactory {

    /**
     * Creates a PaymentMethod based on the specified type.
     *
     * @param type The type of payment method ("credit" or "debit").
     * @param amount The amount to process.
     * @param cardNumber The card number.
     * @param credential For a credit card, this is the expiry date (in "MM/yy" format);
     *                   for a debit card, this is the PIN.
     * @return A PaymentMethod instance.
     * @throws IllegalArgumentException if the type is unrecognized.
     */
    public static PaymentMethod createPaymentMethod(String type, double amount, String cardNumber, String credential) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Payment method type must be specified.");
        }
        
        if (type.equalsIgnoreCase("credit")) {
            return new CreditCard(amount, cardNumber, credential);
        } else if (type.equalsIgnoreCase("debit")) {
            return new DebitCard(amount, cardNumber, credential);
        } else {
            throw new IllegalArgumentException("Unknown payment method type: " + type);
        }
    }
}