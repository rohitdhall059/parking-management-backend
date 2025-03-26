package com.example.parking.model;

public class Student extends Client {
    private String major;
    private String year;

    public Student(String clientId, String name, String email, String password, String major, String year) {
        super(clientId, name, email, password, null, "STUDENT");
        this.major = major;
        this.year = year;
    }

    public Student(String id, String name, String email) {
        //TODO Auto-generated constructor stub
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
} 