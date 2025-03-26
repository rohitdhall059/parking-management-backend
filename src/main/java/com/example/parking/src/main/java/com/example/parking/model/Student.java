package com.example.parking.model;

/**
 * Represents a Student client. 
 * Extends the base Client class, adding specific fields like major/year.
 */
public class Student extends Client implements Observer {
    private String major;
    private String year;
    private String studentId;

    public Student(String clientId, String name, String email, String password, String studentId, String major, String year) {
        super(clientId, name, email, password, null, null); // Initialize with null car and pricing strategy
        this.studentId = studentId;
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
    
    @Override
    public double getParkingRate() {
        return StudentPricing.getRate();
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
                "studentId='" + studentId + '\'' +
                ", major='" + major + '\'' +
                ", year='" + year + '\'' +
                ", " + super.toString() +
                '}';
    }
}