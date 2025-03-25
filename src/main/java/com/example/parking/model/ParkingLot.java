package com.example.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String id;
    private String name;
    private String location;
    private List<ParkingSpace> parkingSpaces;

    public ParkingLot(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.parkingSpaces = new ArrayList<>();
    }

    public void addParkingSpace(ParkingSpace space) {
        parkingSpaces.add(space);
    }

    public void removeParkingSpace(ParkingSpace space) {
        parkingSpaces.remove(space);
    }

    public ParkingSpaceIterator createIterator(boolean isEnabledOnly) {
        return new ParkingSpaceIterator(parkingSpaces, isEnabledOnly);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }
}