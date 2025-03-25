package com.example.parking.model;

public class OccupiedState implements State {
    private Sensor sensor;

    // Constructor to pass in the associated sensor
    public OccupiedState(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public void handleRequest(ParkingSpace parkingSpace) {
        System.out.println("Parking space " + parkingSpace.getId() + " is currently occupied.");
        
        // Check sensor data to see if the parking space is still occupied
        String carInfo = sensor.scanCarInfo();
        
        if (carInfo.equals("No car")) {
            // If no car is detected, the space is now free
            parkingSpace.setOccupied(false, null);
            System.out.println("Parking space " + parkingSpace.getId() + " is now free.");
            // Optionally change state to AvailableState
            parkingSpace.setState(new AvailableState(sensor));
        } else {
            // If a car is still present, print out car info
            System.out.println("Parking space " + parkingSpace.getId() + " still occupied by car with license plate: " + carInfo);
        }
    }
}