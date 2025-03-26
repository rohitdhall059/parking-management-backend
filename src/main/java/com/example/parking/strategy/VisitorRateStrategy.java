package com.example.parking.strategy;

public class VisitorRateStrategy implements ParkingRateStrategy {
    private static final double RATE_PER_HOUR = 15.0;

    @Override
    public double calculateRate(int hours) {
        return hours * RATE_PER_HOUR;
    }
} 