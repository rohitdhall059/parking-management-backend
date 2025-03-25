package com.example.parking.model;

public class FacultyMemberPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 8.0;
    }
}