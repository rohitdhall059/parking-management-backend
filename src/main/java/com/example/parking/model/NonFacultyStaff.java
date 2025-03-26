package com.example.parking.model;

import com.example.parking.model.client.Client;

public class NonFacultyStaff extends Client {
    private String office;
    private String department;

    public NonFacultyStaff(String id, String name, String email, String office, String department) {
        super(id, name, email);
        this.office = office;
        this.department = department;
    }

    public NonFacultyStaff(String id, String name, String email) {
        //TODO Auto-generated constructor stub
    }

    public String getOffice() {
        return office;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getType() {
        return "NON_FACULTY_STAFF";
    }
} 