package com.example.parking.model;

/**
 * Represents a faculty client, extending the base Client class.
 */
public class FacultyMember extends Client implements Observer {
    private String department;
    private String position;

    public FacultyMember(String clientId, String name, String email, String password,
                         String department, String position) {
        super(clientId, name, email, password);
        this.department = department;
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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