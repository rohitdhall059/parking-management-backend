package com.example.parking.state;

import com.example.parking.model.car.Car;
import com.example.parking.model.ParkingSpace;

public class OccupiedState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public OccupiedState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        throw new IllegalStateException("Cannot occupy an already occupied space");
    }

    @Override
    public void vacate() {
        parkingSpace.setOccupied(false, null);
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