package com.example.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpace {
    private String spaceId;
    private boolean isOccupied;
    private boolean isEnabled;
    private double rate;
    private String licensePlate;
    private List<Observer> observers;

    public ParkingSpace(String spaceId, double rate) {
        this.spaceId = spaceId;
        this.rate = rate;
        this.isOccupied = false;
        this.isEnabled = true;
        this.observers = new ArrayList<>();
    }

    // Observer methods
    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // New method to enable the space
    public void enable() {
        if (!isEnabled) {
            this.isEnabled = true;
            notifyObservers();
        }
    }

    // New method to disable the space
    public void disable() {
        this.isEnabled = false;
        this.isOccupied = false;
        this.licensePlate = null;
        notifyObservers();
    }

    // New method to occupy the space
    public void occupy(String licensePlate) {
        if (isEnabled && !isOccupied) {
            this.isOccupied = true;
            this.licensePlate = licensePlate;
            notifyObservers();
        }
    }

    // New method to vacate the space
    public void vacate() {
        if (isOccupied) {
            this.isOccupied = false;
            this.licensePlate = null;
            notifyObservers();
        }
    }

    // Getters/Setters
    public String getSpaceId() {
        return spaceId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public double getRate() {
        return rate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "spaceId='" + spaceId + '\'' +
                ", isOccupied=" + isOccupied +
                ", isEnabled=" + isEnabled +
                ", rate=" + rate +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
    
    // Method to retrieve status as a string
    public String getStatus() {
        return isEnabled ? (isOccupied ? "Occupied" : "Available") : "Disabled";
    }
}