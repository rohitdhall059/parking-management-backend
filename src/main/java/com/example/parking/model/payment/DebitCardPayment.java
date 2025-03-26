package com.example.parking.model.payment;

public class DebitCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;

    public DebitCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void processPayment(double amount) {
        // Implementation for debit card payment processing
        validateCard();
        System.out.println("Processing debit card payment of $" + amount);
        System.out.println("Card holder: " + cardHolderName);
        System.out.println("Card number: ****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)));
    }

    @Override
    public String getPaymentDetails() {
        return "Debit Card Payment - Card holder: " + cardHolderName + 
               ", Card number: ****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4));
    }

    private void validateCard() {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            throw new IllegalStateException("Invalid debit card number");
        }
        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            throw new IllegalStateException("Invalid card holder name");
        }
    }

    // Getters
    public String getCardNumber() { return cardNumber; }
    public String getCardHolderName() { return cardHolderName; }
} 