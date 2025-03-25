package com.example.parking.factory;

import com.example.parking.model.*;

public class PricingStrategyFactory {
    public static PricingStrategy getPricingStrategy(String clientType) {
        switch (clientType.toLowerCase()) {
            case "visitor":
                return new VisitorPricing();
            case "faculty":
                return new FacultyPricing();
            case "staff":
                return new StaffPricing();
            case "student":
                return new StudentPricing();
            default:
                return new VisitorPricing(); // Default to visitor pricing
        }
    }
} 