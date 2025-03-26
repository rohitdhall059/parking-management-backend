package com.example.parking.model;

import com.example.parking.strategy.ParkingRateStrategy;

/**
 * Represents a faculty client, extending the base Client class.
 */
public class FacultyMember extends Client implements Observer {
    private String facultyID;
    private String department;
    private String position;

    public FacultyMember(String email, String password, String name, 
                        ParkingRateStrategy parkingRateStrategy,
                        String facultyID, String department) {
        super(email, password, name, parkingRateStrategy);
        this.facultyID = facultyID;
        this.department = department;
    }

    public String getFacultyID() { return facultyID; }
    public String getDepartment() { return department; }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getParkingRate(){
        return FacultyMemberPricing.getRate();
    }
    
    @Override
    public void update(ParkingSpace parkingSpace) {
        String status = parkingSpace.isOccupied() ? "occupied" : "available";
        System.out.println("Faculty " + getName() + ": Parking space " + parkingSpace.getSpaceId() + " status changed to " + status);
        if (parkingSpace.isOccupied()) {
            System.out.println("Details: " + parkingSpace.getCarInfo());
        }
    }
    @Override
    public String toString() {
        return "FacultyMember{" +
                "department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", " + super.toString() +
                '}';
    }
}