package com.example.parking.model;

import com.example.parking.model.client.Client;

public class FacultyMember extends Client {
    private String department;
    private String position;

    public FacultyMember(String clientId, String name, String email, String password, String department, String position) {
        super(clientId, name, email, password, null, "FACULTY");
        this.department = department;
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public double getDiscountRate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDiscountRate'");
    }
} 