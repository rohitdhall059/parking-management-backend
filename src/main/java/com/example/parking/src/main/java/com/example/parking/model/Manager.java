package com.example.parking.model;

import java.util.List;

public class Manager {
    private String id;
    private String role;
    private String name;

    public Manager(String id, String role, String name) {
        this.id = id;
        this.role = role;
        this.name = name;
    }

    public void manageParkingLot(ParkingLot lot) {
        // Logic to manage the parking lot
        System.out.println("Managing parking lot: " + lot.getName());
    }

    public void manageParkingSpace(ParkingSpace space) {
        // Logic to manage individual parking space
        System.out.println("Managing parking space with ID: " + space.getId());
    }

    public void viewParkingSpaces(ParkingSpaceIterator iterator) {
        while (iterator.hasNext()) {
            ParkingSpace space = iterator.next();
            System.out.println(space);
        }
    }

    // Getters
    public String getId() { return id; }
    public String getRole() { return role; }
    public String getName() { return name; }
}