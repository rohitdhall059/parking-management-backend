package com.example.parking.model.state;

import com.example.parking.model.ParkingSpace;
import com.example.parking.model.state.AvailableState;

public class DisabledState implements ParkingSpaceState {
    @Override
    public void occupy(ParkingSpace space) {
        System.out.println("Cannot occupy disabled parking space " + space.getSpaceId() + ".");
    }

    @Override
    public void vacate(ParkingSpace space) {
        System.out.println("Parking space " + space.getSpaceId() + " is already disabled.");
    }

    @Override
    public void enable(ParkingSpace space) {
        space.setState(new AvailableState());
        System.out.println("Parking space " + space.getSpaceId() + " is now enabled.");
    }

    @Override
    public void disable(ParkingSpace space) {
        System.out.println("Parking space " + space.getSpaceId() + " is already disabled.");
    }
} 