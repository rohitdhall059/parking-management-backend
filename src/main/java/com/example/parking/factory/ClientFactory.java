package com.example.parking.model;

public class ClientFactory {
    public static Client createClient(String type, String clientId, String name, String email, String password, String extra1, String extra2) {
        switch (type.toLowerCase()) {
            case "student":
                return new Student(clientId, name, email, password, extra1, extra2);
            case "faculty":
                return new FacultyMember(clientId, name, email, password, extra1, extra2);
            case "staff":
                return new NonFacultyStaff(clientId, name, email, password, extra1, extra2);
            case "visitor":
                return new Visitor(clientId, name, email, password, extra1);
            default:
                throw new IllegalArgumentException("Invalid client type");
        }
    }
}
