package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodTest {

    /**
     * Approach #1: Test PaymentMethod by creating an anonymous subclass 
     * that implements the abstract method(s).
     */
    @Test
    public void testSetAmount_AnonymousSubclass() {
        // Create an inline subclass implementing the abstract method
        PaymentMethod pm = new PaymentMethod(100.0) {
            @Override
            public void processPayment() {
                // minimal or mock implementation
            }
        };
        // Test setAmount
        pm.setAmount(150.0);
        assertEquals(150.0, pm.getAmount());
    }

    /**
     * Approach #2: Test PaymentMethod logic via a concrete subclass 
     * (e.g., CreditCard) that inherits from PaymentMethod.
     */
    @Test
    public void testSetAmount_WithCreditCard() {
        // If your CreditCard constructor is (double amount, String cardNumber, String expiry)
        CreditCard cc = new CreditCard(200.0, "1234567812345678", "12/25");
        cc.setAmount(300.0);
        assertEquals(300.0, cc.getAmount());
    }

    @Test
    public void testProcessPayment_WithCreditCard() {
        // If processPayment modifies or logs something, you can check that behavior
        CreditCard cc = new CreditCard(50.0, "9999888877776666", "11/24");
        cc.processPayment();
        // Typically, you'd have an assertion here if processPayment changes state 
        // or returns something. If it's just a print, there's nothing to assert.
        assertEquals(50.0, cc.getAmount());
    }
}
