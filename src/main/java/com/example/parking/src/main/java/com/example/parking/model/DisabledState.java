package com.example.parking.model;

public class DisabledState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space, String licensePlate) {
        System.out.println("Cannot occupy disabled parking space " + space.getId());
    }

    @Override
    public void vacate(ParkingSpace space) {
        System.out.println("Cannot vacate disabled parking space " + space.getId());
    }

    @Override
    public void enable(ParkingSpace space) {
        space.setEnabled(true);
        space.setState(new AvailableState());
        System.out.println("Parking space " + space.getId() + " has been enabled.");
    }

    @Override
    public void disable(ParkingSpace space) {
        System.out.println("Parking space " + space.getId() + " is already disabled.");
    }
} 