package com.example.parking.model;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpace {
    private String spaceId;
    private boolean isOccupied;
    private double rate;
    private String carInfo;

    private List<Observer> observers;

    public ParkingSpace(String spaceId, double rate) {
        this.spaceId = spaceId;
        this.rate = rate;
        this.isOccupied = false;
        this.observers = new ArrayList<>();
    }
    // Add observer
    public void attach(Observer observer) {
        observers.add(observer);
    }
    // Remove observer
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    // Notify all observers
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
    // Set occupancy status
    public void setOccupied(boolean status, String carInfo) {
        this.isOccupied = status;
        this.carInfo = carInfo;
        notifyObservers();
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
                ", carInfo='" + carInfo + '\'' +
                '}';
    }
}