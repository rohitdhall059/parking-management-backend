package com.example.parking.model;

import java.util.regex.Pattern;

public class MobilePayment extends PaymentMethod {

    private String phoneNumber; // Phone number associated with the mobile payment

    public MobilePayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void processPayment(double amount) {
        super(amount);

        // 1. Validate the phone number
        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Transaction failed: invalid phone number.");
            return;
        }

        // 2. Perform a pseudo "approval"
        if (amount <= 0) {
            System.out.println("Transaction failed: amount must be greater than 0.");
            return;
        }

        System.out.println("Mobile payment of $" + amount + " approved using phone number " 
            + maskPhoneNumber(phoneNumber) + ".");
        // Additional logic: contacting payment gateway, updating logs, etc.
    }

    /**
     * Checks whether the phone number appears valid (basic check: 10 digits).
     */
    private boolean isValidPhoneNumber(String number) {
        // Basic check: must be exactly 10 digits (you can adjust this based on your requirements)
        return Pattern.matches("\\d{10}", number);
    }

    /**
     * Replace all but the last four digits of the phone number with '*'.
     */
    private String maskPhoneNumber(String number) {
        if (number.length() <= 4) {
            // If somehow the phone number is too short, just return it.
            return number;
        }
        String last4 = number.substring(number.length() - 4);
        // For a 10-digit phone number: "**** **** 1234"
        return "******" + last4;
    }
}