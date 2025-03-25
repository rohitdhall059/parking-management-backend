package com.example.parking.model;

/**
 * Represents a faculty client, extending the base Client class.
 */
public class FacultyMember extends Client implements Observer {
    private String department;
    private String facultyId;

    public FacultyMember(String clientId, String name, String email, String password,
                         String facultyId, String department) {
        super(clientId, name, email, password, null, null); // Initialize with null car and pricing strategy
        this.department = department;
        this.facultyId = facultyId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public double getParkingRate() {
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
                ", facultyId='" + facultyId + '\'' +
                ", " + super.toString() +
                '}';
    }
}