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
    private ParkingSpaceState state;

    public ParkingSpace(String spaceId, double rate) {
        this.spaceId = spaceId;
        this.rate = rate;
        this.isOccupied = false;
        this.isEnabled = true;
        this.observers = new ArrayList<>();
        this.state = new AvailableState();
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
            this.state = new AvailableState();
            notifyObservers();
        }
    }

    // New method to disable the space
    public void disable() {
        this.isEnabled = false;
        this.isOccupied = false;
        this.licensePlate = null;
        this.state = new DisabledState();
        notifyObservers();
    }

    // New method to occupy the space
    public void occupy(String licensePlate) {
        if (isEnabled && !isOccupied) {
            this.isOccupied = true;
            this.licensePlate = licensePlate;
            this.state = new OccupiedState();
            notifyObservers();
        }
    }

    // New method to vacate the space
    public void vacate() {
        if (isOccupied) {
            this.isOccupied = false;
            this.licensePlate = null;
            this.state = new AvailableState();
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

    public ParkingSpaceState getState() {
        return state;
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

    public void setState(ParkingSpaceState state) {
        this.state = state;
    }

    public void setOccupied(boolean occupied, Car car) {
        this.isOccupied = occupied;
        if (car != null) {
            this.licensePlate = car.getLicensePlate();
        } else {
            this.licensePlate = null;
        }
        notifyObservers();
    }

    public void setRate(double rate) {
        this.rate = rate;
        notifyObservers();
    }

    public String getCarInfo() {
        return isOccupied ? "Car with license plate: " + licensePlate : "No car";
    }

    public String getId() {
        return spaceId;
    }

    public void free() {
        this.isOccupied = false;
        this.licensePlate = null;
        this.state = new AvailableState();
        notifyObservers();
    }
}