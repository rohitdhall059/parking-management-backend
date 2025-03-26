package com.example.parking.model;

public class StaffPricing implements PricingStrategy {
    private static final double RATE = 3.0; // $3 per hour for staff members

    @Override
    public double getRate() {
        return RATE;
    }
} 