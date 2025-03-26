package com.example.parking.model.state;

import com.example.parking.model.ParkingSpace;

public interface ParkingSpaceState {
    void occupy(ParkingSpace space);
    void vacate(ParkingSpace space);
    void enable(ParkingSpace space);
    void disable(ParkingSpace space);
} 