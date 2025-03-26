package com.example.parking.model.pricing;

public class FacultyPricing implements PricingStrategy {
    private static final double RATE = 2.0; // $2 per hour

    @Override
    public double getRate() {
        return RATE;
    }

    @Override
    public String getType() {
        return "FACULTY";
    }
} 