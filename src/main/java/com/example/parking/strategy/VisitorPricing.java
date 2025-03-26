package com.example.parking.strategy;

public class VisitorPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 5.0; // $5 per hour for visitors
    }
} 