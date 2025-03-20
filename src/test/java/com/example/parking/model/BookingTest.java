package com.example.parking.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @Test
    public void testConstructorSetsFields() {
        LocalDateTime start = LocalDateTime.of(2025, 3, 20, 10, 0);
        Booking b = new Booking("BK100", "C100", "SP100", start);
        assertEquals("BK100", b.getBookingId());
        assertEquals("C100", b.getClientId());
        assertEquals("SP100", b.getSpaceId());
        assertEquals(start, b.getStartTime());
        assertNull(b.getEndTime());
    }

    @Test
    public void testSetTotalCost() {
        Booking b = new Booking("BK101", "C101", "SP101", LocalDateTime.now());
        b.setTotalCost(45.0);
        assertEquals(45.0, b.getTotalCost());
    }

    @Test
    public void testSetEndTime() {
        Booking b = new Booking("BK102", "C102", "SP102", LocalDateTime.now());
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        b.setEndTime(end);
        assertEquals(end, b.getEndTime());
    }
}
