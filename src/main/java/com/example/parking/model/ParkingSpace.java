package com.example.parking.model;

public class ParkingSpace {
    private String spaceId;
    private boolean isOccupied;
    private double rate;

    public ParkingSpace(String spaceId, double rate) {
        this.spaceId = spaceId;
        this.rate = rate;
        this.isOccupied = false;
    }

    // Getters/Setters
    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }

    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "spaceId='" + spaceId + '\'' +
                ", isOccupied=" + isOccupied +
                ", rate=" + rate +
                '}';
    }
}