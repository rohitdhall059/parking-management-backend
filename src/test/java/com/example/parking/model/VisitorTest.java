package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VisitorTest {

    @Test
    public void testConstructor() {
        Visitor v = new Visitor("V100", "Visiting Person", "visitor@univ.edu", "secret", "CampusTour", "VID123");
        assertEquals("V100", v.getClientId());
        assertEquals("Visiting Person", v.getName());
        assertEquals("CampusTour", v.getVisitingPurpose());
        assertEquals("VID123", v.getVisitorId());
    }
}
