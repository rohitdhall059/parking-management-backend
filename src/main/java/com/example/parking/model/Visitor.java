package com.example.parking.model;

import com.example.parking.strategy.ParkingRateStrategy;
import java.util.Date;
/**
 * Represents a visitor client, extending the base Client class.
 */
public class Visitor extends Client implements Observer {
    private String visitorID;
    private Date visitDate;
    private String visitInformation;

    public Visitor(String email, String password, String name, 
                  ParkingRateStrategy parkingRateStrategy,
                  String visitorID, Date visitDate, String visitInformation) {
        super(email, password, name, parkingRateStrategy);
        this.visitorID = visitorID;
        this.visitDate = visitDate;
        this.visitInformation = visitInformation;
    }

    public String getVisitorID() { return visitorID; }
    public Date getVisitDate() { return visitDate; }
    public String getVisitInformation() { return visitInformation; }

    public double getParkingRate(){
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
                ", visitorID='" + visitorID + '\'' +
                ", visitDate=" + visitDate +
                ", " + super.toString() +
                '}';
    }
}