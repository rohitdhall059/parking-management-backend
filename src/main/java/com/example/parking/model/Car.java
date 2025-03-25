package com.example.parking.model;

public class Car {
    private String model;         // The car's model
    private String licensePlate;  // The car's license plate

    // Constructor
    public Car(String model, String licensePlate) {
        this.model = model;
        this.licensePlate = licensePlate;
    }

    // Getters
    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "Car Model: " + model + ", License Plate: " + licensePlate;
    }
}