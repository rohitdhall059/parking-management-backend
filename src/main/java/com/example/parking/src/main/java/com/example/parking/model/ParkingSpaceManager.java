package com.example.parking.model;

import java.util.HashMap;
import java.util.Map;

public class ParkingSpaceManager {
    private static ParkingSpaceManager instance;
    private Map<String, ParkingSpace> parkingSpaces;

    private ParkingSpaceManager() {
        parkingSpaces = new HashMap<>();
    }

    public static ParkingSpaceManager getInstance() {
        if (instance == null) {
            instance = new ParkingSpaceManager();
        }
        return instance;
    }

    public void addParkingSpace(ParkingSpace space) {
        parkingSpaces.put(space.getId(), space);
    }

    public ParkingSpace getParkingSpace(String spaceId) {
        return parkingSpaces.get(spaceId);
    }

    public void removeParkingSpace(String spaceId) {
        parkingSpaces.remove(spaceId);
    }

    public Map<String, ParkingSpace> getAllParkingSpaces() {
        return new HashMap<>(parkingSpaces);
    }
} 