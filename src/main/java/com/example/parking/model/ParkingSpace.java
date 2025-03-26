package com.example.parking.model;

import com.example.parking.model.car.Car;
import com.example.parking.model.state.ParkingSpaceState;
import com.example.parking.model.state.AvailableState;
import com.example.parking.model.state.OccupiedState;
import com.example.parking.model.state.DisabledState;
import com.example.parking.observer.Observer;
import com.example.parking.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpace implements Subject {
    private final String id;
    private final String location;
    private final String type;
    private ParkingSpaceState state;
    private boolean enabled;
    private double rate;
    private List<Observer> observers;

    public ParkingSpace(String id, String location, String type) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.state = new AvailableState(this);
        this.enabled = true;
        this.rate = 0.0;
        this.observers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public boolean isOccupied() {
        return state instanceof OccupiedState;
    }

    public void setOccupied(boolean occupied, Car car) {
        if (occupied) {
            state = new OccupiedState(this, car);
        } else {
            state = new AvailableState(this);
        }
        notifyObservers();
    }

    public Car getCurrentCar() {
        return state instanceof OccupiedState ? ((OccupiedState) state).getCar() : null;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            state = new DisabledState(this);
        } else if (!isOccupied()) {
            state = new AvailableState(this);
        }
        notifyObservers();
    }

    public void enable() {
        state.enable();
    }

    public void disable() {
        state.disable();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ParkingSpaceState getState() {
        return state;
    }

    public void setState(ParkingSpaceState state) {
        this.state = state;
        notifyObservers();
    }

    public boolean isAvailable() {
        return enabled && !isOccupied();
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