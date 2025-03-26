package com.example.parking.model;

import java.util.Random;
import java.util.logging.Logger;

public class Sensor implements Observer {
    private static final Logger logger = Logger.getLogger(Sensor.class.getName());
    private String sensorId;
    private ParkingSpace parkingSpace;
    private boolean isActive;
    private Random random = new Random();

    // Constructor
    public Sensor(String sensorId, ParkingSpace parkingSpace) {
        this.sensorId = sensorId;
        this.parkingSpace = parkingSpace;
        this.isActive = true;
        attachToParkingSpace();
    }

    // Attach this sensor as an observer to the parking space
    private void attachToParkingSpace() {
        parkingSpace.attach(this);
        logger.info("Sensor attached to parking space: " + parkingSpace.getSpaceId());
    }

    @Override
    public void update(ParkingSpace parkingSpace) {
        if (isActive) {
            System.out.println("Sensor " + sensorId + " detected change in parking space " + parkingSpace.getId());
            // Additional logic for sensor updates
        }
    }

    // Method to detect the presence of a car by scanning for its license plate
    public void detectCarPresence(String licensePlate) {
        boolean carDetected = scanForCar(licensePlate);
        updateParkingSpace(carDetected, licensePlate);
    }

    // Simulate scanning for a car based on the license plate
    private boolean scanForCar(String licensePlate) {
        // Simulate a detection logic (in a real scenario, this would involve hardware)
        boolean detected = random.nextBoolean(); // Randomly simulates detection
        logger.info("Scanning for car with license plate " + licensePlate + ": " + detected);
        return detected;
    }

    // Update the parking space based on car presence
    private void updateParkingSpace(boolean occupied, String licensePlate) {
        if (occupied) {
            // Create a new Car object based on the detected license plate
            Car car = new Car("Detected Model", licensePlate); // Replace "Detected Model" with actual model if known
            parkingSpace.setOccupied(true, car); // Set the parking space as occupied with the car
            logger.info("Parking space occupied: " + parkingSpace.getSpaceId() + " with car: " + car);
        } else {
            parkingSpace.setOccupied(false, null); // Free the parking space
            logger.info("Parking space freed: " + parkingSpace.getSpaceId());
        }
    }

    public String scanCarInfo() {
        if (!isActive) {
            return "Sensor inactive";
        }
        return parkingSpace.isOccupied() ? parkingSpace.getLicensePlate() : "No car";
    }

    public void activate() {
        isActive = true;
        System.out.println("Sensor " + sensorId + " activated");
    }

    public void deactivate() {
        isActive = false;
        System.out.println("Sensor " + sensorId + " deactivated");
    }

    public String getSensorId() {
        return sensorId;
    }

    public boolean isActive() {
        return isActive;
    }

    // Getters and setters if needed
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }
}