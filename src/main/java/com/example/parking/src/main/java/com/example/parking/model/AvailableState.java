package com.example.parking.model;

public class AvailableState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space, String licensePlate) {
        if (space.isEnabled()) {
            space.setOccupied(true, new Car(licensePlate));
            space.setState(new OccupiedState());
            System.out.println("Parking space " + space.getId() + " is now occupied by car with license plate: " + licensePlate);
        } else {
            System.out.println("Cannot occupy disabled parking space " + space.getId());
        }
    }

    @Override
    public void vacate(ParkingSpace space) {
        System.out.println("Parking space " + space.getId() + " is already available.");
    }

    @Override
    public void enable(ParkingSpace space) {
        System.out.println("Parking space " + space.getId() + " is already enabled.");
    }

    @Override
    public void disable(ParkingSpace space) {
        space.setEnabled(false);
        space.setState(new DisabledState());
        System.out.println("Parking space " + space.getId() + " has been disabled.");
    }
}