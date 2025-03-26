package com.example.parking.strategy;

public class StudentPricing implements PricingStrategy {
    @Override
    public double getRate() {
        return 1.5; // $1.50 per hour for students
    }
} 