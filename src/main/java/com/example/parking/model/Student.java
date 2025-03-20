package com.example.parking.model;

/**
 * Represents a Student client. 
 * Extends the base Client class, adding specific fields like major/year.
 */
public class Student extends Client {
    private String major;
    private String year;

    public Student(String clientId, String name, String email, String password, 
                   String major, String year) {
        // Call the Client constructor with 4 parameters
        super(clientId, name, email, password);
        this.major = major;
        this.year = year;
    }

    // If you prefer no password:
    // public Student(String clientId, String name, String email, String major, String year) {
    //     super(clientId, name, email);
    //     this.major = major;
    //     this.year = year;
    // }

    // Getters and setters
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

    @Override
    public String toString() {
        return "Student{" +
                "major='" + major + '\'' +
                ", year='" + year + '\'' +
                ", " + super.toString() +
                '}';
    }
}