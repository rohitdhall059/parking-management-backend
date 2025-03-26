package com.example.parking.model;

/**
 * Represents non-faculty staff, extending the base Client class.
 */
public class NonFacultyStaff extends Client implements Observer {
    private String role;
    private String department;

    public NonFacultyStaff(String clientId, String name, String email, String password,
                           String role, String department) {
        super(clientId, name, email, password);
        this.role = role;
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return office;
    }

    public void setDepartment(String office) {
        this.department = department;
    }

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
                "role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", " + super.toString() +
                '}';
    }
}