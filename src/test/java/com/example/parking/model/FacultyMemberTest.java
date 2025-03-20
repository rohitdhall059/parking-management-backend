package com.example.parking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FacultyMemberTest {

    @Test
    public void testConstructor() {
        FacultyMember fm = new FacultyMember("F100", "Dr. Smith", "smith@univ.edu", "p@ss", "Physics", "Professor");
        assertEquals("F100", fm.getClientId());
        assertEquals("Dr. Smith", fm.getName());
        assertEquals("smith@univ.edu", fm.getEmail());
        assertEquals("Physics", fm.getDepartment());
        assertEquals("Professor", fm.getPosition());
    }
}