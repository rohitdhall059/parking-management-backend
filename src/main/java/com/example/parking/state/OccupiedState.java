package com.example.parking.state;

import com.example.parking.model.Car;
import com.example.parking.model.ParkingSpace;

public class OccupiedState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public OccupiedState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is already occupied");
    }

    @Override
    public void vacate() {
        parkingSpace.setOccupied(false, null);
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is now available");
    }

    @Override
    public void enable() {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is already enabled");
    }

    @Override
    public void disable() {
        parkingSpace.setEnabled(false);
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is now disabled");
    }
} 