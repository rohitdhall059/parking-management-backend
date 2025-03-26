package com.example.parking.state;

import com.example.parking.model.ParkingSpace;

public interface State {
    void handleRequest(ParkingSpace parkingSpace);
} 