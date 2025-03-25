package com.example.parking.model;

public class DebitCard extends PaymentMethod {

    private String cardNumber;
    private String cardHolderName;
    private String pin; // e.g. "1234"

    public DebitCard(String cardNumber, String cardHolderName, String pin) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.pin = pin;
    }

    @Override
    public void processPayment(double amount) {
        super(amount);
        
        // 1. Validate the debit card number
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Transaction failed: invalid debit card number.");
            return;
        }

        // 2. Validate the PIN
        if (!isValidPin(pin)) {
            System.out.println("Transaction failed: incorrect PIN.");
            return;
        }

        // 3. Mask the card number
        String masked = maskCardNumber(cardNumber);

        // 4. Check amount
        if (amount <= 0) {
            System.out.println("Transaction failed: amount must be greater than 0.");
            return;
        }

        // 5. Approve payment
        System.out.println("Debit card payment of $" + amount + " approved using card " 
            + masked + " with valid PIN.");
        // Additional logic: deduct from bank account, record transaction, etc.
    }

    /**
     * Example validation: a 16-digit numeric card number.
     */
    private boolean isValidCardNumber(String number) {
        return number.matches("\\d{16}");
    }

    /**
     * Example PIN validation: exactly 4 digits.
     */
    private boolean isValidPin(String pin) {
        return pin.matches("\\d{4}");
    }

    /**
     * Same masking approach as in CreditCard, so we don't display the full card number.
     */
    private String maskCardNumber(String number) {
        if (number.length() <= 4) {
            return number;
        }
        String last4 = number.substring(number.length() - 4);
        return "************" + last4;
    }
}