package com.example.parking.factory;

import com.example.parking.model.pricing.FacultyPricing;
import com.example.parking.model.pricing.StaffPricing;
import com.example.parking.model.pricing.StudentPricing;
import com.example.parking.model.pricing.VisitorPricing;
import com.example.parking.model.pricing.PricingStrategy;

public class PricingStrategyFactory {
    public static PricingStrategy getPricingStrategy(String clientType) {
        switch (clientType.toUpperCase()) {
            case "VISITOR":
                return new VisitorPricing();
            case "FACULTY":
                return new FacultyPricing();
            case "STAFF":
                return new StaffPricing();
            case "STUDENT":
                return new StudentPricing();
            default:
                return new VisitorPricing();
        }
    }
} 