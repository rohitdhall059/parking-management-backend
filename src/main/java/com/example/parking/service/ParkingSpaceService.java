package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.ParkingSpace;
import java.util.List;
import java.util.UUID;

/**
 * Handles parking space operations:
 *   - Retrieve available spaces
 *   - Mark a space occupied/free
 *   - Potentially update rates, etc.
 */
public class ParkingSpaceService {

    private final DAO<ParkingSpace> parkingSpaceDAO;

    public ParkingSpaceService(DAO<ParkingSpace> parkingSpaceDAO) {
        this.parkingSpaceDAO = parkingSpaceDAO;
    }

    public ParkingSpace createParkingSpace(String location, String type) {
        String spaceId = UUID.randomUUID().toString();
        ParkingSpace space = new ParkingSpace(spaceId, location, type);
        parkingSpaceDAO.save(space);
        return space;
    }

    public ParkingSpace getParkingSpace(String spaceId) {
        return parkingSpaceDAO.getById(spaceId);
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceDAO.getAll();
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpaceDAO.getAll().stream()
                .filter(ParkingSpace::isAvailable)
                .toList();
    }

    public void updateParkingSpace(ParkingSpace space) {
        if (space == null) {
            throw new IllegalArgumentException("Parking space cannot be null");
        }
        parkingSpaceDAO.update(space);
    }

    public void deleteParkingSpace(String spaceId) {
        parkingSpaceDAO.delete(spaceId);
    }

    public void enableParkingSpace(String spaceId) {
        ParkingSpace space = getParkingSpace(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found");
        }
        space.setEnabled(true);
        parkingSpaceDAO.update(space);
    }

    public void disableParkingSpace(String spaceId) {
        ParkingSpace space = getParkingSpace(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found");
        }
        space.setEnabled(false);
        parkingSpaceDAO.update(space);
    }

    /**
     * Returns a list of all currently unoccupied parking spaces.
     */
    public List<ParkingSpace> getAvailableSpaces() {
        List<ParkingSpace> allSpaces = parkingSpaceDAO.getAll();
        List<ParkingSpace> available = allSpaces.stream()
                .filter(ParkingSpace::isAvailable)
                .toList();
        return available;
    }

    /**
     * Mark a space as occupied, if it exists and is free.
     */
    public void markSpaceOccupied(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        if (space.isOccupied()) {
            throw new IllegalStateException("Space " + spaceId + " is already occupied.");
        }

        space.setOccupied(true);
        parkingSpaceDAO.update(space);
    }

    /**
     * Mark a space as free (unoccupied).
     */
    public void markSpaceFree(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        if (!space.isOccupied()) {
            throw new IllegalStateException("Space " + spaceId + " is already free.");
        }

        space.setOccupied(false);
        parkingSpaceDAO.update(space);
    }

    /**
     * Optionally update the rate or other properties.
     */
    public void updateSpaceRate(String spaceId, double newRate) {
        if (newRate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative.");
        }
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        space.setRate(newRate);
        parkingSpaceDAO.update(space);
    }

    /**
     * Retrieve all spaces, if needed for the GUI.
     */
    public List<ParkingSpace> getAllSpaces() {
        return parkingSpaceDAO.getAll();
    }
}