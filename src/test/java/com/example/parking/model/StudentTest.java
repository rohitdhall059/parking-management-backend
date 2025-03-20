package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testConstructor() {
        Student s = new Student("ST100", "Alice Student", "alice@student.edu", "password", "CS", "Year2");
        assertEquals("ST100", s.getClientId());
        assertEquals("Alice Student", s.getName());
        assertEquals("CS", s.getMajor());
        assertEquals("Year2", s.getYear());
    }
}
