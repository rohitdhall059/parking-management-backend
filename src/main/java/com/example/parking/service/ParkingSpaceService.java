package com.example.parking.service;

import com.example.parking.model.ParkingSpace;
import com.example.parking.dao.ParkingSpaceDAO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles parking space operations:
 *   - Retrieve available spaces
 *   - Mark a space occupied/free
 *   - Potentially update rates, etc.
 */
public class ParkingSpaceService {

    private final ParkingSpaceDAO parkingSpaceDAO;

    public ParkingSpaceService(ParkingSpaceDAO parkingSpaceDAO) {
        this.parkingSpaceDAO = parkingSpaceDAO;
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceDAO.getAll();
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpaceDAO.getAll().stream()
                .filter(ParkingSpace::isAvailable)
                .collect(Collectors.toList());
    }

    public ParkingSpace getParkingSpace(String spaceId) {
        return parkingSpaceDAO.getById(spaceId);
    }

    public void updateParkingSpaceStatus(String spaceId, boolean isOccupied) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space != null) {
            space.setOccupied(isOccupied, null);
            parkingSpaceDAO.update(space);
        }
    }

    public void updateParkingSpaceRate(String spaceId, double newRate) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space != null) {
            space.setRate(newRate);
            parkingSpaceDAO.update(space);
        }
    }

    public void enableParkingSpace(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space != null) {
            space.setEnabled(true);
            parkingSpaceDAO.update(space);
        }
    }

    public void disableParkingSpace(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space != null) {
            space.setEnabled(false);
            parkingSpaceDAO.update(space);
        }
    }

    /**
     * Returns a list of all currently unoccupied parking spaces.
     */
    public List<ParkingSpace> getAvailableSpaces() {
        return parkingSpaceDAO.getAll().stream()
                .filter(space -> !space.isOccupied() && space.isEnabled())
                .toList();
    }

    /**
     * Mark a space as occupied, if it exists and is free.
     */
    public void markSpaceOccupied(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found: " + spaceId);
        }
        space.setOccupied(true, null);
        parkingSpaceDAO.update(space);
    }

    /**
     * Mark a space as free (unoccupied).
     */
    public void markSpaceFree(String spaceId) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found: " + spaceId);
        }
        space.setOccupied(false, null);
        parkingSpaceDAO.update(space);
    }

    /**
     * Optionally update the rate or other properties.
     */
    public void updateSpaceRate(String spaceId, double newRate) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found: " + spaceId);
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

    public void updateSpaceStatus(String spaceId, boolean isEnabled) {
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found: " + spaceId);
        }
        space.setEnabled(isEnabled);
        parkingSpaceDAO.update(space);
    }

    public void enableSpace(String spaceId) {
        updateSpaceStatus(spaceId, true);
    }

    public void disableSpace(String spaceId) {
        updateSpaceStatus(spaceId, false);
    }
}