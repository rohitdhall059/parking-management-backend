package com.example.parking.model;

public class BookedState implements State {
    @Override
    public void handleRequest(ParkingSpace parkingSpace) {
        System.out.println("Parking space " + parkingSpace.getId() + " has been booked.");
        parkingSpace.setState(this); // Set this state as the current state
    }
}