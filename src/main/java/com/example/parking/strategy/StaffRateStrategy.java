package com.example.parking.strategy;

public class StaffRateStrategy implements ParkingRateStrategy {
    private static final double RATE_PER_HOUR = 10.0;

    @Override
    public double calculateRate(int hours) {
        return hours * RATE_PER_HOUR;
    }
} 