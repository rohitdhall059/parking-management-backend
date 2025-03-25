package com.example.parking.model;

public class VisitorPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 15.0;
    }
}