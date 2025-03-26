package com.example.parking.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCard extends PaymentMethod {

    private String cardNumber;
    private String cardHolderName;
    private String expiry; // Expected format: "MM/yy", e.g. "03/25"

    public CreditCard(String cardHolderName, String cardNumber, String expiry) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
    }

    @Override
    public void processPayment(double amount) {
        super(amount);

        // 1. Validate the credit card number
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Transaction failed: invalid credit card number.");
            return;
        }

        // 2. Check if the card is expired
        if (isCardExpired(expiry)) {
            System.out.println("Transaction failed: credit card is expired (" + expiry + ").");
            return;
        }

        // 3. Mask the card number for display
        String maskedCard = maskCardNumber(cardNumber);

        // 4. Perform a pseudo "approval"
        if (amount <= 0) {
            System.out.println("Transaction failed: amount must be greater than 0.");
            return;
        }

        System.out.println("Credit card payment of $" + amount + " approved using card " 
            + maskedCard + " (expires " + expiry + ").");
        // Additional logic: contacting payment gateway, updating logs, etc.
    }

    /**
     * Checks whether the card number appears valid (length of 16 digits, all numeric).
     */
    private boolean isValidCardNumber(String number) {
        // Basic check: must be exactly 16 digits
        return number.matches("\\d{16}");
    }

    /**
     * Parses the expiry string (MM/yy) and checks if it is before the current month.
     */
    private boolean isCardExpired(String expiryStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth cardExpiry = YearMonth.parse(expiryStr, formatter);
            YearMonth now = YearMonth.now();

            // If the card's expiry is before the current month, it's expired
            return cardExpiry.isBefore(now);
        } catch (DateTimeParseException e) {
            // If we can't parse, treat as expired
            return true;
        }
    }

    /**
     * Replace all but the last four digits of the card number with '*'.
     */
    private String maskCardNumber(String number) {
        if (number.length() <= 4) {
            // If somehow the card number is too short, just return it.
            return number;
        }
        String last4 = number.substring(number.length() - 4);
        // For a 16-digit card: "**** **** **** 1234" (or some variation).
        // We'll just do a simpler approach with 12 stars:
        return "************" + last4;
    }
}