package com.example.parking.state;

import com.example.parking.model.car.Car;

public interface ParkingSpaceState {
    void occupy(Car car);
    void vacate();
    void enable();
    void disable();
} 