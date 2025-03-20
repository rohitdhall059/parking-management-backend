package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testConstructor() {
        Client c = new Client("C100", "Alice", "alice@example.com");
        assertEquals("C100", c.getClientId());
        assertEquals("Alice", c.getName());
        assertEquals("alice@example.com", c.getEmail());
        assertFalse(c.isRegistered());
    }

    @Test
    public void testSetRegistered() {
        Client c = new Client("C101", "Bob", "bob@example.com");
        c.setRegistered(true);
        assertTrue(c.isRegistered());
    }

    @Test
    public void testSetName() {
        Client c = new Client("C102", "Charlie", "charlie@example.com");
        c.setName("Charles");
        assertEquals("Charles", c.getName());
    }
}
