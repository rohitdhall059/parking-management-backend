package com.example.parking.model;

public interface State {
    void handleRequest(ParkingSpace parkingSpace);
}