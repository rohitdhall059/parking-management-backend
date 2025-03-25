package com.example.parking.model;

public class AvailableState implements State {
    @Override
    public void handleRequest(ParkingSpace parkingSpace) {
        System.out.println("Parking space " + parkingSpace.getId() + " is available.");
        // Logic to book the space
        parkingSpace.setState(new BookedState());
    }
}