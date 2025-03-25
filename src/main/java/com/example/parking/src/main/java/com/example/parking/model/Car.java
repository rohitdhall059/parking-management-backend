package com.example.parking.model;

public class Car {
    private String licensePlate;
    private String make;
    private String model;
    private String color;

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Car(String model, String licensePlate) {
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public Car(String licensePlate, String make, String model, String color) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}