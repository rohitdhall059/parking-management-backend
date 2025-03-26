package com.example.parking.state;

import com.example.parking.model.car.Car;
import com.example.parking.model.ParkingSpace;

public class DisabledState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public DisabledState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        throw new IllegalStateException("Cannot occupy a disabled space");
    }

    @Override
    public void vacate() {
        throw new IllegalStateException("Cannot vacate a disabled space");
    }

    @Override
    public void enable() {
        parkingSpace.setEnabled(true);
    }

    @Override
    public void disable() {
        throw new IllegalStateException("Space is already disabled");
    }
} 