package com.example.parking.state;

import com.example.parking.model.Car;
import com.example.parking.model.ParkingSpace;

public class DisabledState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public DisabledState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is disabled");
    }

    @Override
    public void vacate() {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is disabled");
    }

    @Override
    public void enable() {
        parkingSpace.setEnabled(true);
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is now enabled");
    }

    @Override
    public void disable() {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is already disabled");
    }
} 