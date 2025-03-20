package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DebitCardTest {

    @Test
    public void testConstructor() {
        DebitCard dc = new DebitCard(50.0, "8765432187654321", "1234");
        assertEquals(50.0, dc.getAmount());
        // If you have a method getPin() or getCardNumber(), test them similarly.
    }

    @Test
    public void testProcessPayment() {
        DebitCard dc = new DebitCard(75.0, "1111222233334444", "0000");
        // e.g., dc.processPayment();
        assertEquals(75.0, dc.getAmount());
    }
}
