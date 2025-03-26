package com.example.parking.model;

public class BookedState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space, String licensePlate) {
        System.out.println("Parking space " + space.getId() + " is already booked.");
    }

    @Override
    public void vacate(ParkingSpace space) {
        space.setOccupied(false, null);
        space.setState(new AvailableState());
        System.out.println("Parking space " + space.getId() + " is now available.");
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