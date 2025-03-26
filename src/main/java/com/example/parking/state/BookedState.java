package com.example.parking.state;

import com.example.parking.model.ParkingSpace;

public class BookedState implements State {
    @Override
    public void handleRequest(ParkingSpace parkingSpace) {
        if (parkingSpace.isOccupied()) {
            parkingSpace.setState(new OccupiedState());
        } else {
            parkingSpace.setState(new AvailableState());
        }
    }
} 