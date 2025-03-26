package com.example.parking.factory;

import com.example.parking.model.NonFacultyStaff;
import com.example.parking.model.Student;
import com.example.parking.model.Visitor;
import com.example.parking.model.client.Client;
import com.example.parking.model.client.FacultyMember;

/**
 * A factory class for creating different types of clients 
 * based on a string identifier (student, faculty, staff, visitor).
 */
public class ClientFactory {

    /**
     * Creates a Client (or subclass) based on the 'type'.
     *
     * @param type      e.g., "student", "faculty", "staff", "visitor"
     * @param id        unique ID for the client
     * @param name      client's name
     * @param email     client's email
     * @return          a new Client subclass instance
     * @throws IllegalArgumentException if the type is invalid
     */
    public static Client createClient(String type, String id, String name, String email) {
        return switch (type.toUpperCase()) {
            case "FM" -> new FacultyMember(id, name, email);
            case "ST" -> new Student(id, name, email);
            case "NF" -> new NonFacultyStaff(id, name, email);
            case "VI" -> new Visitor(id, name, email);
            default -> throw new IllegalArgumentException("Unknown client type: " + type);
        };
    }
}
