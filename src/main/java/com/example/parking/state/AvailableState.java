package com.example.parking.state;

import com.example.parking.model.Car;
import com.example.parking.model.ParkingSpace;

public class AvailableState implements ParkingSpaceState {
    private ParkingSpace parkingSpace;

    public AvailableState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void occupy(Car car) {
        parkingSpace.setOccupied(true, car);
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is now occupied by " + car.getLicensePlate());
    }

    @Override
    public void vacate() {
        System.out.println("Parking space " + parkingSpace.getSpaceId() + " is already available");
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