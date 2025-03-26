package com.example.parking.model.pricing;

public class VisitorPricing implements PricingStrategy {
    private static final double RATE = 5.0; // $5 per hour

    @Override
    public double getRate() {
        return RATE;
    }

    @Override
    public String getType() {
        return "VISITOR";
    }
} 