package com.example.parking.model;

import com.example.parking.strategy.ParkingRateStrategy;

/**
 * Represents non-faculty staff, extending the base Client class.
 */
public class NonFacultyStaff extends Client implements Observer {
    private String staffID;
    private String department;

    public NonFacultyStaff(String email, String password, String name, 
                          ParkingRateStrategy parkingRateStrategy,
                          String staffID, String department) {
        super(email, password, name, parkingRateStrategy);
        this.staffID = staffID;
        this.department = department;
    }

    public String getStaffID() { return staffID; }
    public String getDepartment() { return department; }

    public double getParkingRate(){
        return NonFacultyStaffPricing.getRate();
    }
    @Override
    public void update(ParkingSpace parkingSpace) {
        String status = parkingSpace.isOccupied() ? "occupied" : "available";
        System.out.println("NonFacultyStaff " + getName() + ": Parking space " + parkingSpace.getSpaceId() + " status changed to " + status);
        if (parkingSpace.isOccupied()) {
            System.out.println("Details: " + parkingSpace.getCarInfo());
        }
    }
    @Override
    public String toString() {
        return "NonFacultyStaff{" +
                "staffID='" + staffID + '\'' +
                ", department='" + department + '\'' +
                ", " + super.toString() +
                '}';
    }
}