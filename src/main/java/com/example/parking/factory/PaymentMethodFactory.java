package com.example.parking.factory;

import com.example.parking.model.payment.PaymentMethod;
import com.example.parking.model.payment.CreditCard;
import com.example.parking.model.payment.DebitCard;
import com.example.parking.model.payment.MobilePayment;

public class PaymentMethodFactory {

    /**
     * Creates a PaymentMethod based on the specified type.
     *
     * @param type The type of payment method ("credit", "debit", or "mobile").
     * @param cardNumber The card number or phone number.
     * @param credential For a credit card, this is the expiry date (in "MM/yy" format);
     *                   for a debit card, this is the PIN;
     *                   for mobile payment, this is the card holder name.
     * @return A PaymentMethod instance.
     * @throws IllegalArgumentException if the type is unrecognized.
     */
    public static PaymentMethod createPaymentMethod(String type, String cardNumber, String credential) {
        return switch (type.toLowerCase()) {
            case "credit" -> new CreditCard(cardNumber, credential);
            case "debit" -> new DebitCard(cardNumber, credential);
            case "mobile" -> new MobilePayment(credential);
            default -> throw new IllegalArgumentException("Unknown payment method type: " + type);
        };
    }
}