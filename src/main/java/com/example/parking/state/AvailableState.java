package com.example.parking.state;

import com.example.parking.model.car.Car;
import com.example.parking.model.ParkingSpace;

public class AvailableState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public AvailableState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        parkingSpace.setOccupied(true, car);
    }

    @Override
    public void vacate() {
        throw new IllegalStateException("Cannot vacate an already available space");
    }

    @Override
    public void enable() {
        throw new IllegalStateException("Space is already enabled");
    }

    @Override
    public void disable() {
        parkingSpace.setEnabled(false);
    }
} 