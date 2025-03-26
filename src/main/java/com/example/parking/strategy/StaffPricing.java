package com.example.parking.strategy;

public class StaffPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 3.0; // $3 per hour for staff
    }
} 