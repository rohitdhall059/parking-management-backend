package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NonFacultyStaffTest {

    @Test
    public void testConstructor() {
        NonFacultyStaff staff = new NonFacultyStaff("S200", "Admin Joe", "joe@univ.edu", "pass123", "Administrator", "MainOffice");
        assertEquals("S200", staff.getClientId());
        assertEquals("Admin Joe", staff.getName());
        assertEquals("Administrator", staff.getRole());
        assertEquals("MainOffice", staff.getOffice());
    }
}