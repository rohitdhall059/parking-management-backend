package com.example.parking.model;

public class NonFacultyStaffPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 10.0;
    }
}