package com.example.parking.model;
import java.util.Date;
/**
 * Represents a visitor client, extending the base Client class.
 */
public class Visitor extends Client implements Observer {
    private String visitInformation;
    private String visitorId;
    private Date visitDate;

    public Visitor(String clientId, String name, String email, String password,
                   String visitInformation, String visitorId) {
        super(clientId, name, email, password, null, null); // Initialize with null car and pricing strategy
        this.visitInformation = visitInformation;
        this.visitorId = visitorId;
    }

    public String getvisitInformation() {
        return visitInformation;
    }

    public void setvisitInformation(String visitInformation) {
        this.visitInformation = visitInformation;
    }
    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Date getVisitDate() {  // Getter for visitDate
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {  // Setter for visitDate
        this.visitDate = visitDate;
    }

    @Override
    public double getParkingRate() {
        return VisitorPricing.getRate();
    }

    @Override
    public void update(ParkingSpace parkingSpace) {
        // Handle the parking space update notification
        String status = parkingSpace.isOccupied() ? "occupied" : "available";
        System.out.println("Visitor " + name + ": Parking space status changed to " + status);
        if (parkingSpace.isOccupied()) {
            System.out.println("Details: " + parkingSpace.getCarInfo());
        }
    }
    @Override
    public String toString() {
        return "Visitor{" +
                "visitInformation='" + visitInformation + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", visitDate=" + visitDate +
                ", " + super.toString() +
                '}';
    }
}