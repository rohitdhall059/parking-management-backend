package com.example.parking.factory;

import com.example.parking.model.Client;
import com.example.parking.model.Student;
import com.example.parking.model.FacultyMember;
import com.example.parking.model.NonFacultyStaff;
import com.example.parking.model.Visitor;

/**
 * A factory class for creating different types of clients 
 * based on a string identifier (student, faculty, staff, visitor).
 */
public class ClientFactory {

    /**
     * Creates a Client (or subclass) based on the 'type'.
     *
     * @param type      e.g., "student", "faculty", "staff", "visitor"
     * @param clientId  unique ID for the client
     * @param name      client's name
     * @param email     client's email
     * @param password  optional password
     * @param extra1    specialized field (e.g. major for students, department for faculty, etc.)
     * @param extra2    specialized field (e.g. year for students, position for faculty, etc.)
     * @return          a new Client subclass instance
     * @throws IllegalArgumentException if the type is invalid
     */
    public static Client createClient(String type, 
                                      String clientId, 
                                      String name, 
                                      String email, 
                                      String password, 
                                      String extra1, 
                                      String extra2) 
    {
        if (type == null) {
            throw new IllegalArgumentException("Client type cannot be null");
        }
        switch (type.toLowerCase()) {
            case "student":
                return new Student(clientId, name, email, password, extra1, extra2);
            case "faculty":
                return new FacultyMember(clientId, name, email, password, extra1, extra2);
            case "staff":
                return new NonFacultyStaff(clientId, name, email, password, extra1, extra2);
            case "visitor":
                return new Visitor(clientId, name, email, password, extra1, extra2);
            default:
                throw new IllegalArgumentException("Invalid client type: " + type);
        }
    }
}
