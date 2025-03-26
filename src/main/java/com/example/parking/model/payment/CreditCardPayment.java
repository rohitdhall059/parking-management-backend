package com.example.parking.model.payment;

// Strategy Pattern: Concrete strategy for credit card payments
public class CreditCardPayment extends AbstractPayment {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        super();
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    protected boolean processSpecificPayment(double amount) {
        // Implement credit card specific payment logic
        if (amount <= 0) return false;
        notifyObservers("Processing credit card payment for " + cardHolderName);
        return true;
    }

    @Override
    protected boolean processSpecificRefund(double amount) {
        // Implement credit card specific refund logic
        if (amount <= 0 || amount > balance) return false;
        notifyObservers("Processing credit card refund for " + cardHolderName);
        return true;
    }

    @Override
    public boolean validatePayment() {
        return cardNumber != null && 
               cardNumber.matches("\\d{16}") && 
               cardHolderName != null && 
               !cardHolderName.trim().isEmpty();
    }

    @Override
    public String getPaymentDetails() {
        return "Credit Card Payment - Holder: " + cardHolderName + 
               ", Number: ****" + cardNumber.substring(cardNumber.length() - 4);
    }

    // Getters
    public String getCardNumber() { return cardNumber; }
    public String getCardHolderName() { return cardHolderName; }
} 