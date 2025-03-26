package com.example.parking.model;

import java.util.Random;
import java.util.logging.Logger;

public class Sensor {
    private static final Logger logger = Logger.getLogger(Sensor.class.getName());
    private ParkingSpace parkingSpace;
    private Random random = new Random();

    // Constructor
    public Sensor(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        attachToParkingSpace();
    }

    // Attach this sensor as an observer to the parking space
    private void attachToParkingSpace() {
        parkingSpace.attach(this);
        logger.info("Sensor attached to parking space: " + parkingSpace.getSpaceId());
    }

    // Method to detect the presence of a car by scanning for its license plate
    public void detectCarPresence() {
        // Simulates car detection
        boolean carDetected = true; // In real implementation, this would use actual sensors
        if (carDetected) {
            String carInfo = scanCarInfo();
            updateParkingSpace(carInfo);
        }
    }

    // Simulate scanning for a car based on the license plate
    private boolean scanForCar(String licensePlate) {
        // Simulate a detection logic (in a real scenario, this would involve hardware)
        boolean detected = random.nextBoolean(); // Randomly simulates detection
        logger.info("Scanning for car with license plate " + licensePlate + ": " + detected);
        return detected;
    }

    // Update the parking space based on car presence
    private void updateParkingSpace(String carInfo) {
        if (carInfo != null && !carInfo.isEmpty()) {
            parkingSpace.occupy(carInfo);
            logger.info("Parking space occupied: " + parkingSpace.getSpaceId() + " with car: " + carInfo);
        } else {
            parkingSpace.vacate();
            logger.info("Parking space freed: " + parkingSpace.getSpaceId());
        }
    }

    public String scanCarInfo() {
        // Simulates scanning car information
        return "ABC123"; // In real implementation, this would scan actual car info
    }

    // Getters and setters if needed
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }
}