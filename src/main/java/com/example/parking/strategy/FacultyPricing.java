package com.example.parking.strategy;

public class FacultyPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 2.0; // $2 per hour for faculty
    }
} 