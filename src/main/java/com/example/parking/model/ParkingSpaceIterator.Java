package com.example.parking.model;

import java.util.List;

public class ParkingSpaceIterator {
    private List<ParkingSpace> parkingSpaces;
    private int position;
    private boolean isEnabledOnly;

    public ParkingSpaceIterator(List<ParkingSpace> parkingSpaces, boolean isEnabledOnly) {
        this.parkingSpaces = parkingSpaces;
        this.position = 0;
        this.isEnabledOnly = isEnabledOnly;
    }

    public boolean hasNext() {
        while (position < parkingSpaces.size()) {
            ParkingSpace space = parkingSpaces.get(position);
            if (!isEnabledOnly || (isEnabledOnly && space.isEnabled)) {
                return true;
            }
            position++;
        }
        return false;
    }

    public ParkingSpace next() {
        ParkingSpace space = parkingSpaces.get(position);
        position++;
        return space;
    }
}