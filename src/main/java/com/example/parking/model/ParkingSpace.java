package com.example.parking.model;

import com.example.parking.observer.Observer;
import com.example.parking.observer.Subject;
import com.example.parking.state.*;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpace implements Subject {
    private String spaceId;
    private String location;
    private String type;
    private boolean isOccupied;
    private boolean isEnabled;
    private Car parkedCar;
    private ParkingSpaceState state;
    private List<Observer> observers;

    public ParkingSpace(String spaceId, String location, String type) {
        this.spaceId = spaceId;
        this.location = location;
        this.type = type;
        this.isOccupied = false;
        this.isEnabled = true;
        this.observers = new ArrayList<>();
        this.state = new AvailableState(this);
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void setOccupied(boolean occupied, Car car) {
        this.isOccupied = occupied;
        this.parkedCar = car;
        if (occupied) {
            state = new OccupiedState(this);
        } else {
            state = new AvailableState(this);
        }
        notifyObservers();
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        if (enabled) {
            state = new AvailableState(this);
        } else {
            state = new DisabledState(this);
        }
        notifyObservers();
    }

    public void occupy(Car car) {
        state.occupy(car);
    }

    public void vacate() {
        state.vacate();
    }

    public void enable() {
        state.enable();
    }

    public void disable() {
        state.disable();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
} 