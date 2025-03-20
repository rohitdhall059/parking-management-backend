package com.example.parking.factory;

import com.example.parking.model.PaymentMethod;
import com.example.parking.model.CreditCard;
import com.example.parking.model.DebitCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodFactoryTest {

    @Test
    public void testCreateCreditCard() {
        // Suppose PaymentMethodFactory signature is:
        // createPaymentMethod(String type, double amount, String cardNumber, String credential)
        PaymentMethod pm = PaymentMethodFactory.createPaymentMethod("credit",
                100.0, "1234567812345678", "12/25");

        assertNotNull(pm);
        assertTrue(pm instanceof CreditCard);
        assertEquals(100.0, pm.getAmount());

        // If CreditCard has getCardNumber() or getExpiry(), cast and check:
        // CreditCard cc = (CreditCard) pm;
        // assertEquals("1234567812345678", cc.getCardNumber());
        // assertEquals("12/25", cc.getExpiry());
    }

    @Test
    public void testCreateDebitCard() {
        PaymentMethod pm = PaymentMethodFactory.createPaymentMethod("debit",
                50.0, "8765432187654321", "1234");

        assertNotNull(pm);
        assertTrue(pm instanceof DebitCard);
        assertEquals(50.0, pm.getAmount());

        // If DebitCard has getPin() or getCardNumber(), cast and check:
        // DebitCard dc = (DebitCard) pm;
        // assertEquals("1234", dc.getPin());
    }

    @Test
    public void testCreateUnknownType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            PaymentMethodFactory.createPaymentMethod("paypal", 200.0, "N/A", "N/A");
        });
        assertTrue(ex.getMessage().contains("Unknown payment method type"));
    }
}