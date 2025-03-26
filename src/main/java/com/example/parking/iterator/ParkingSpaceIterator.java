package com.example.parking.iterator;

import java.util.List;
import com.example.parking.model.ParkingSpace;

public class ParkingSpaceIterator implements Iterator<ParkingSpace> {
    private List<ParkingSpace> parkingSpaces;
    private int position;
    private boolean enabledOnly;

    public ParkingSpaceIterator(List<ParkingSpace> parkingSpaces, boolean enabledOnly) {
        this.parkingSpaces = parkingSpaces;
        this.position = 0;
        this.enabledOnly = enabledOnly;
    }

    @Override
    public boolean hasNext() {
        while (position < parkingSpaces.size()) {
            ParkingSpace space = parkingSpaces.get(position);
            if (!enabledOnly || space.isEnabled()) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public ParkingSpace next() {
        if (!hasNext()) {
            return null;
        }
        return parkingSpaces.get(position++);
    }
} 