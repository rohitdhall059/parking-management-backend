package com.example.parking.model;

public class StudentPricing implements PricingStrategy {
    private static final double RATE = 1.0; // $1 per hour for students

    @Override
    public double getRate() {
        return RATE;
    }
}