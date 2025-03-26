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
        if (!parkingLots.contains(lot)) {
            parkingLots.add(lot);
        }
        System.out.println("Managing parking lot: " + lot.getName());
        // Initialize status for all spaces in the lot
        for (ParkingSpace space : lot.getParkingSpaces()) {
            parkingSpaceStatus.put(space.getId(), space.isEnabled());
        }
    }

    public void manageParkingSpace(ParkingSpace space) {
        parkingSpaceStatus.put(space.getId(), space.isEnabled());
        System.out.println("Managing parking space with ID: " + space.getId());
        System.out.println("Current status: " + (space.isEnabled() ? "Enabled" : "Disabled"));
    }

    public void viewParkingSpaces(ParkingSpaceIterator iterator) {
        System.out.println("\nParking Spaces Status Report:");
        System.out.println("------------------------------");
        while (iterator.hasNext()) {
            ParkingSpace space = iterator.next();
            System.out.println("Space ID: " + space.getId());
            System.out.println("Status: " + (space.isEnabled() ? "Enabled" : "Disabled"));
            System.out.println("Occupied: " + (space.isOccupied() ? "Yes" : "No"));
            if (space.isOccupied()) {
                System.out.println("Vehicle Info: " + space.getCarInfo());
            }
            System.out.println("------------------------------");
        }
    }

    public boolean validateClientRegistration(Client client) {
        if (client == null) {
            return false;
        }
        
        // Basic validation checks
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            return false;
        }
        
        // Check if email is in valid format
        if (!client.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }
        
        // Additional validation based on client type
        if (client instanceof Student) {
            return validateStudent((Student) client);
        } else if (client instanceof FacultyMember) {
            return validateFacultyMember((FacultyMember) client);
        } else if (client instanceof NonFacultyStaff) {
            return validateNonFacultyStaff((NonFacultyStaff) client);
        } else if (client instanceof Visitor) {
            return validateVisitor((Visitor) client);
        }
        
        return false;
    }

    private boolean validateStudent(Student student) {
        return student.getStudentID() != null && !student.getStudentID().trim().isEmpty() &&
               student.getDepartment() != null && !student.getDepartment().trim().isEmpty();
    }

    private boolean validateFacultyMember(FacultyMember faculty) {
        return faculty.getFacultyID() != null && !faculty.getFacultyID().trim().isEmpty() &&
               faculty.getDepartment() != null && !faculty.getDepartment().trim().isEmpty();
    }

    private boolean validateNonFacultyStaff(NonFacultyStaff staff) {
        return staff.getStaffID() != null && !staff.getStaffID().trim().isEmpty() &&
               staff.getDepartment() != null && !staff.getDepartment().trim().isEmpty();
    }

    private boolean validateVisitor(Visitor visitor) {
        return visitor.getVisitorID() != null && !visitor.getVisitorID().trim().isEmpty() &&
               visitor.getVisitDate() != null;
    }

    public void addParkingLot(ParkingLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Parking lot cannot be null");
        }
        if (!parkingLots.contains(lot)) {
            parkingLots.add(lot);
            System.out.println("Added parking lot: " + lot.getName());
            // Initialize status for all spaces in the new lot
            for (ParkingSpace space : lot.getParkingSpaces()) {
                parkingSpaceStatus.put(space.getId(), space.isEnabled());
            }
        } else {
            System.out.println("Parking lot already exists: " + lot.getName());
        }
    }

    public void enableParkingLot(ParkingLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Parking lot cannot be null");
        }
        if (parkingLots.contains(lot)) {
            for (ParkingSpace space : lot.getParkingSpaces()) {
                space.enable();
                parkingSpaceStatus.put(space.getId(), true);
            }
            System.out.println("Enabled parking lot: " + lot.getName());
        } else {
            throw new IllegalStateException("Parking lot not found: " + lot.getName());
        }
    }

    public void disableParkingLot(ParkingLot lot) {
        if (lot == null) {
            throw new IllegalArgumentException("Parking lot cannot be null");
        }
        if (parkingLots.contains(lot)) {
            for (ParkingSpace space : lot.getParkingSpaces()) {
                space.disable();
                parkingSpaceStatus.put(space.getId(), false);
            }
            System.out.println("Disabled parking lot: " + lot.getName());
        } else {
            throw new IllegalStateException("Parking lot not found: " + lot.getName());
        }
    }

    public void enableParkingSpace(ParkingSpace space) {
        if (space == null) {
            throw new IllegalArgumentException("Parking space cannot be null");
        }
        space.enable();
        parkingSpaceStatus.put(space.getId(), true);
        System.out.println("Enabled parking space: " + space.getId());
    }

    public void disableParkingSpace(ParkingSpace space) {
        if (space == null) {
            throw new IllegalArgumentException("Parking space cannot be null");
        }
        space.disable();
        parkingSpaceStatus.put(space.getId(), false);
        System.out.println("Disabled parking space: " + space.getId());
    }

    // Additional utility methods
    public boolean isParkingSpaceEnabled(String spaceId) {
        return parkingSpaceStatus.getOrDefault(spaceId, false);
    }

    public List<ParkingLot> getParkingLots() {
        return new ArrayList<>(parkingLots); // Return a copy to maintain encapsulation
    }

    // Getters
    public String getId() { return id; }
    public String getRole() { return role; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "ManagementTeam{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", parkingLots=" + parkingLots.size() +
                ", managedSpaces=" + parkingSpaceStatus.size() +
                '}';
    }
}