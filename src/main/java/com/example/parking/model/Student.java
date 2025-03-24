package com.example.parking.model;

/**
 * Represents a Student client. 
 * Extends the base Client class, adding specific fields like major/year.
 */
public class Student extends Client implements Observer {
    private String major;
    private String year;
    private String department;

    public Student(String clientId, String name, String email, String password, String department, String major, String year) {
        super(clientId, name, email, password);
        this.department = department;
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
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    @Override
    public void update(ParkingSpace parkingSpace) {
        String status = parkingSpace.isOccupied() ? "occupied" : "available";
        System.out.println("Student " + getName() + ": Parking space " + parkingSpace.getSpaceId() + " status changed to " + status);
        if (parkingSpace.isOccupied()) {
            System.out.println("Details: " + parkingSpace.getCarInfo());
        }
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