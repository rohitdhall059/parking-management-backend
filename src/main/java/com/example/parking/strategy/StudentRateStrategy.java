package com.example.parking.strategy;

public class StudentRateStrategy implements ParkingRateStrategy {
    private static final double RATE_PER_HOUR = 5.0;

    @Override
    public double calculateRate(int hours) {
        return hours * RATE_PER_HOUR;
    }
} 