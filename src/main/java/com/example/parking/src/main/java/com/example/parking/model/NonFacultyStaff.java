package com.example.parking.model;

/**
 * Represents non-faculty staff, extending the base Client class.
 */
public class NonFacultyStaff extends Client implements Observer {
    private String staffId;
    private String office;

    public NonFacultyStaff(String clientId, String name, String email, String password,
                           String staffId, String office) {
        super(clientId, name, email, password, null, null); // Initialize with null car and pricing strategy
        this.staffId = staffId;
        this.office = office;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public double getParkingRate() {
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
                "staffId='" + staffId + '\'' +
                ", office='" + office + '\'' +
                ", " + super.toString() +
                '}';
    }
}