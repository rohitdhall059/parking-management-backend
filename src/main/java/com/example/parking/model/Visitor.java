package com.example.parking.model;

/**
 * Represents a visitor client, extending the base Client class.
 */
public class Visitor extends Client {
    private String visitingPurpose;
    private String visitorId;

    public Visitor(String clientId, String name, String email, String password,
                   String visitingPurpose, String visitorId) {
        super(clientId, name, email, password);
        this.visitingPurpose = visitingPurpose;
        this.visitorId = visitorId;
    }

    public String getVisitingPurpose() {
        return visitingPurpose;
    }

    public void setVisitingPurpose(String visitingPurpose) {
        this.visitingPurpose = visitingPurpose;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "visitingPurpose='" + visitingPurpose + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", " + super.toString() +
                '}';
    }
}