package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.ParkingSpace;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles parking space operations:
 *   - Retrieve available spaces
 *   - Mark a space occupied/free
 *   - Potentially update rates, etc.
 */
public class ParkingSpaceService {

    private final DAO<ParkingSpace> spaceDAO;

    public ParkingSpaceService(DAO<ParkingSpace> spaceDAO) {
        this.spaceDAO = spaceDAO;
    }

    /**
     * Returns a list of all currently unoccupied parking spaces.
     */
    public List<ParkingSpace> getAvailableSpaces() {
        List<ParkingSpace> allSpaces = spaceDAO.getAll();
        List<ParkingSpace> available = new ArrayList<>();
        for (ParkingSpace s : allSpaces) {
            if (!s.isOccupied()) {
                available.add(s);
            }
        }
        return available;
    }

    /**
     * Mark a space as occupied, if it exists and is free.
     */
    public void markSpaceOccupied(String spaceId) {
        ParkingSpace space = spaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        if (space.isOccupied()) {
            throw new IllegalStateException("Space " + spaceId + " is already occupied.");
        }

        space.setOccupied(true);
        spaceDAO.update(space);
    }

    /**
     * Mark a space as free (unoccupied).
     */
    public void markSpaceFree(String spaceId) {
        ParkingSpace space = spaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        if (!space.isOccupied()) {
            throw new IllegalStateException("Space " + spaceId + " is already free.");
        }

        space.setOccupied(false);
        spaceDAO.update(space);
    }

    /**
     * Optionally update the rate or other properties.
     */
    public void updateSpaceRate(String spaceId, double newRate) {
        if (newRate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative.");
        }
        ParkingSpace space = spaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space found with ID: " + spaceId);
        }
        space.setRate(newRate);
        spaceDAO.update(space);
    }

    /**
     * Retrieve all spaces, if needed for the GUI.
     */
    public List<ParkingSpace> getAllSpaces() {
        return spaceDAO.getAll();
    }
}