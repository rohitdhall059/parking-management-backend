package com.example.parking.model.state;

import com.example.parking.model.ParkingSpace;

public interface ParkingSpaceObserver {
    void update(ParkingSpace space);
} 