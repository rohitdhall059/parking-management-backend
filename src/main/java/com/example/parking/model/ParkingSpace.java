package com.example.parking.model;

import java.util.ArrayList;
import java.util.List;
import com.example.parking.observer.Observer;
import com.example.parking.state.State;
import com.example.parking.state.AvailableState;

public class ParkingSpace {
    private String id;
    private String licenseplate;
    private boolean isOccupied;
    private boolean isEnabled;
    private double rate;
    private State state;
    private List<Observer> observers;
    private String carInfo;
    private Sensor sensor;

    public ParkingSpace(String id, double rate) {
        this.id = id;
        this.rate = rate;
        this.isOccupied = false;
        this.isEnabled = true;
        this.state = new AvailableState();
        this.observers = new ArrayList<>();
        this.sensor = new Sensor(this);
    }

    // Observer Pattern methods
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

    // State Pattern methods
    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handleRequest(this);
    }

    // Space management methods
    public void enable() {
        isEnabled = true;
        notifyObservers();
    }

    public void disable() {
        isEnabled = false;
        notifyObservers();
    }

    public void occupy(String licensePlate) {
        this.licenseplate = licensePlate;
        this.isOccupied = true;
        this.carInfo = "License Plate: " + licensePlate;
        notifyObservers();
    }

    public void vacate() {
        this.licenseplate = null;
        this.isOccupied = false;
        this.carInfo = null;
        notifyObservers();
    }

    public void setOccupied(boolean status, String carInfo) {
        this.isOccupied = status;
        this.carInfo = carInfo;
        notifyObservers();
    }

    // Getters and setters
    public String getId() { return id; }
    public boolean isOccupied() { return isOccupied; }
    public boolean isEnabled() { return isEnabled; }
    public double getRate() { return rate; }
    public String getCarInfo() { return carInfo; }
    public String getLicensePlate() { return licenseplate; }
    public State getState() { return state; }
    public Sensor getSensor() { return sensor; }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "id='" + id + '\'' +
                ", isOccupied=" + isOccupied +
                ", isEnabled=" + isEnabled +
                ", rate=" + rate +
                ", carInfo='" + carInfo + '\'' +
                ", state=" + state +
                ", sensor=" + sensor +
                '}';
    }
    
    // Method to retrieve status as a string
    public String getStatus() {
        return isEnabled ? (isOccupied ? "Occupied" : "Available") : "Disabled";
    }
}