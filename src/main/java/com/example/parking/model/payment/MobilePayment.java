package com.example.parking.model.payment;

public class MobilePayment implements PaymentMethod {
    private String phoneNumber;

    public MobilePayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void processPayment(double amount) {
        // Implementation for mobile payment processing
        validatePhoneNumber();
        System.out.println("Processing mobile payment of $" + amount);
        System.out.println("Phone number: " + formatPhoneNumber());
    }

    @Override
    public String getPaymentDetails() {
        return "Mobile Payment - Phone number: " + formatPhoneNumber();
    }

    private void validatePhoneNumber() {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalStateException("Invalid phone number format. Must be 10 digits.");
        }
    }

    private String formatPhoneNumber() {
        return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }

    // Getter
    public String getPhoneNumber() { return phoneNumber; }
} 