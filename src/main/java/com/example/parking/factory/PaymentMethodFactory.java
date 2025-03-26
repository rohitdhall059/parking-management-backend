package com.example.parking.factory;

import com.example.parking.model.payment.*;

// Factory Pattern + Singleton Pattern
public class PaymentMethodFactory {
    private static PaymentMethodFactory instance;
    
    private PaymentMethodFactory() {}
    
    public static PaymentMethodFactory getInstance() {
        if (instance == null) {
            instance = new PaymentMethodFactory();
        }
        return instance;
    }

    /**
     * Creates a PaymentMethod based on the specified type and parameters.
     *
     * @param type The type of payment method ("credit", "debit", "mobile", or "cash")
     * @param params Additional parameters needed for the payment method:
     *        - For credit/debit cards: [cardNumber, cardHolderName]
     *        - For mobile payment: [phoneNumber]
     *        - For cash payment: [amount]
     * @return A PaymentMethod instance
     * @throws IllegalArgumentException if the type is unrecognized or parameters are invalid
     */
    public PaymentMethod createPayment(String type, String... params) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Payment type cannot be null or empty");
        }

        PaymentMethod payment = null;
        switch (type.toLowerCase()) {
            case "credit":
            case "creditcard":
                validateCardParams(params);
                payment = new CreditCardPayment(params[0], params[1]);
                break;
                
            case "debit":
            case "debitcard":
                validateCardParams(params);
                payment = new DebitCardPayment(params[0], params[1]);
                break;
                
            case "mobile":
            case "phone":
                validateMobileParams(params);
                payment = new MobilePayment(params[0]);
                break;
                
            case "cash":
                payment = new CashPayment(params.length > 0 ? Double.parseDouble(params[0]) : 0.0);
                break;
                
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + type);
        }

        // Add default observers if needed
        // payment.addObserver(new PaymentLogger());
        // payment.addObserver(new PaymentNotifier());
        
        return payment;
    }

    private void validateCardParams(String[] params) {
        if (params == null || params.length < 2) {
            throw new IllegalArgumentException("Card payments require card number and holder name");
        }
        if (!params[0].matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number format");
        }
        if (params[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Card holder name cannot be empty");
        }
    }

    private void validateMobileParams(String[] params) {
        if (params == null || params.length < 1) {
            throw new IllegalArgumentException("Mobile payment requires phone number");
        }
        if (!params[0].matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
}