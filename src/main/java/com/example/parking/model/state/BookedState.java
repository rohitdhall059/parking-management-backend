package com.example.parking.model.state;

import com.example.parking.model.ParkingSpace;
import com.example.parking.model.state.AvailableState;

public class BookedState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space) {
        System.out.println("Parking space " + space.getSpaceId() + " is already booked.");
    }

    @Override
    public void vacate(ParkingSpace space) {
        space.setState(new AvailableState());
        System.out.println("Parking space " + space.getSpaceId() + " is now available.");
    }

    @Override
    public void enable(ParkingSpace space) {
        System.out.println("Parking space " + space.getSpaceId() + " is already enabled.");
    }

    @Override
    public void disable(ParkingSpace space) {
        space.setState(new DisabledState());
        System.out.println("Parking space " + space.getSpaceId() + " is now disabled.");
    }
} 