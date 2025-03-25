package com.example.parking.model;

public class StudentPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 5.0;
    }
}