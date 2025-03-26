package com.example.parking.model.state;

import com.example.parking.model.ParkingSpace;
import com.example.parking.model.state.BookedState;

public class AvailableState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space) {
        space.setState(new BookedState());
        System.out.println("Parking space " + space.getSpaceId() + " is now occupied.");
    }

    @Override
    public void vacate(ParkingSpace space) {
        System.out.println("Parking space " + space.getSpaceId() + " is already available.");
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