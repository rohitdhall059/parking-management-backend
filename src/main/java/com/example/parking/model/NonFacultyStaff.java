package com.example.parking.model;

/**
 * Represents non-faculty staff, extending the base Client class.
 */
public class NonFacultyStaff extends Client {
    private String role;
    private String office;

    public NonFacultyStaff(String clientId, String name, String email, String password,
                           String role, String office) {
        super(clientId, name, email, password);
        this.role = role;
        this.office = office;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "NonFacultyStaff{" +
                "role='" + role + '\'' +
                ", office='" + office + '\'' +
                ", " + super.toString() +
                '}';
    }
}