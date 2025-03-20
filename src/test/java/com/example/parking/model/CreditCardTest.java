package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {

    @Test
    public void testConstructor() {
        CreditCard cc = new CreditCard(100.0, "1234567812345678", "12/25");
        assertEquals(100.0, cc.getAmount());
        // If you have getters for cardNumber/expiry, test them:
        // assertEquals("1234567812345678", cc.getCardNumber());
        // assertEquals("12/25", cc.getExpiry());
    }

    @Test
    public void testProcessPayment() {
        CreditCard cc = new CreditCard(200.0, "9999888877776666", "11/24");
        // If processPayment modifies state or returns something, test that.
        // e.g. cc.processPayment();
        assertEquals(200.0, cc.getAmount());
    }
}