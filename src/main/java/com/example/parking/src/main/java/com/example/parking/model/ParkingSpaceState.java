package com.example.parking.model;

public interface ParkingSpaceState {
    void occupy(ParkingSpace space, String licensePlate);
    void vacate(ParkingSpace space);
    void enable(ParkingSpace space);
    void disable(ParkingSpace space);
} 