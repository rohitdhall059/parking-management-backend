package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpaceTest {

    @Test
    public void testConstructor() {
        ParkingSpace ps = new ParkingSpace("SP100", 3.5);
        assertEquals("SP100", ps.getSpaceId());
        assertEquals(3.5, ps.getRate());
        assertFalse(ps.isOccupied());
    }

    @Test
    public void testSetOccupied() {
        ParkingSpace ps = new ParkingSpace("SP200", 4.0);
        ps.setOccupied(true);
        assertTrue(ps.isOccupied());
    }

    @Test
    public void testSetRate() {
        ParkingSpace ps = new ParkingSpace("SP300", 2.5);
        ps.setRate(3.0);
        assertEquals(3.0, ps.getRate());
    }
}
