package com.example.parking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagementTeam {
    private String id;
    private String role;
    private String name;

    private List<ParkingLot> parkingLots;
    private HashMap<String, Boolean> parkingSpaceStatus;

    public ManagementTeam(String id, String role, String name) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.parkingLots = new ArrayList<>();
        this.parkingSpaceStatus = new HashMap<>();
    }

    public void manageParkingLot(ParkingLot lot) {
        System.out.println("Managing parking lot: " + lot.getName());
    }

    public void manageParkingSpace(ParkingSpace space) {
        System.out.println("Managing parking space with ID: " + space.getId());
    }

    public void viewParkingSpaces(ParkingSpaceIterator iterator) {
        while (iterator.hasNext()) {
            ParkingSpace space = iterator.next();
            System.out.println(space);
        }
    }

    // Methods for managing clients and parking lots
    public boolean validateClientRegistration(Client client) {
        System.out.println("Validating registration for: " + client.getEmail());
        // Assume the validation logic checks against existing records
        return client.getEmail() != null && client.getPassword() != null; // Example validation
    }

    public void addParkingLot(ParkingLot lot) {
        parkingLots.add(lot);
        System.out.println("Added parking lot: " + lot.getName());
    }

    public void enableParkingLot(ParkingLot lot) {
        if (parkingLots.contains(lot)) {
            lot.setEnabled(true);
            System.out.println("Enabled parking lot: " + lot.getName());
        } else {
            System.out.println("Parking lot: " + lot.getName() + " does not exist.");
        }
    }

    public void disableParkingLot(ParkingLot lot) {
        if (parkingLots.contains(lot)) {
            lot.setEnabled(false);
            System.out.println("Disabled parking lot: " + lot.getName());
        } else {
            System.out.println("Parking lot: " + lot.getName() + " does not exist.");
        }
    }

    public void enableParkingSpace(ParkingSpace space) {
        parkingSpaceStatus.put(space.getId(), true);
        System.out.println("Enabled parking space with ID: " + space.getId());
    }

    public void disableParkingSpace(ParkingSpace space) {
        parkingSpaceStatus.put(space.getId(), false);
        System.out.println("Disabled parking space with ID: " + space.getId());
    }

    // Getters
    public String getId() { return id; }
    public String getRole() { return role; }
    public String getName() { return name; }
}