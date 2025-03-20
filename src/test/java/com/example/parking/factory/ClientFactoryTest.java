package com.example.parking.factory;

import com.example.parking.model.Client;
import com.example.parking.model.Student;
import com.example.parking.model.FacultyMember;
import com.example.parking.model.NonFacultyStaff;
import com.example.parking.model.Visitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientFactoryTest {

    @Test
    public void testCreateStudent() {
        // Suppose your ClientFactory signature is:
        // createClient(String type, String clientId, String name, String email, String password, String extra1, String extra2)
        Client c = ClientFactory.createClient("student",
                "ST100", "Alice Student", "alice@univ.edu", "pass123",
                "ComputerScience", "Year2");

        assertNotNull(c);
        assertTrue(c instanceof Student);
        assertEquals("ST100", c.getClientId());
        assertEquals("Alice Student", c.getName());

        // If Student has fields major/year, cast and check them:
        Student s = (Student) c;
        assertEquals("ComputerScience", s.getMajor());
        assertEquals("Year2", s.getYear());
    }

    @Test
    public void testCreateFacultyMember() {
        Client c = ClientFactory.createClient("faculty",
                "F100", "Dr. Smith", "smith@univ.edu", "secret",
                "Physics", "Professor");

        assertNotNull(c);
        assertTrue(c instanceof FacultyMember);
        FacultyMember fm = (FacultyMember) c;
        assertEquals("F100", fm.getClientId());
        assertEquals("Dr. Smith", fm.getName());
        assertEquals("Physics", fm.getDepartment());
        assertEquals("Professor", fm.getPosition());
    }

    @Test
    public void testCreateStaff() {
        Client c = ClientFactory.createClient("staff",
                "S200", "Admin Joe", "joe@univ.edu", "adminpass",
                "Administrator", "MainOffice");

        assertNotNull(c);
        assertTrue(c instanceof NonFacultyStaff);
        NonFacultyStaff staff = (NonFacultyStaff) c;
        assertEquals("S200", staff.getClientId());
        assertEquals("Admin Joe", staff.getName());
        assertEquals("Administrator", staff.getRole());
        assertEquals("MainOffice", staff.getOffice());
    }

    @Test
    public void testCreateVisitor() {
        Client c = ClientFactory.createClient("visitor",
                "V300", "Visiting Person", "visitor@univ.edu", "visitorpass",
                "CampusTour", "VID123");

        assertNotNull(c);
        assertTrue(c instanceof Visitor);
        Visitor v = (Visitor) c;
        assertEquals("V300", v.getClientId());
        assertEquals("Visiting Person", v.getName());
        assertEquals("CampusTour", v.getVisitingPurpose());
        assertEquals("VID123", v.getVisitorId());
    }

    @Test
    public void testCreateUnknownType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            ClientFactory.createClient("alien", "A100", "ET", "et@univ.edu", "etpass", "???", "???");
        });
        assertTrue(ex.getMessage().contains("Invalid client type"));
    }
}