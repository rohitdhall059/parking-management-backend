package com.example.parking.model.pricing;

public class StudentPricing implements PricingStrategy {
    private static final double RATE = 1.0; // $1 per hour

    @Override
    public double getRate() {
        return RATE;
    }

    @Override
    public String getType() {
        return "STUDENT";
    }
} 