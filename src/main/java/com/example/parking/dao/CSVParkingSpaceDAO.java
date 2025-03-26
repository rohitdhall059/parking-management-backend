package com.example.parking.dao;

import com.example.parking.model.ParkingSpace;
import com.example.parking.model.car.Car;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParkingSpaceDAO implements ParkingSpaceDAO {
    private final String filePath;

    public CSVParkingSpaceDAO() {
        this.filePath = "parking_spaces.csv";
    }

    @Override
    public void save(ParkingSpace space) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(formatParkingSpace(space));
        } catch (IOException e) {
            throw new RuntimeException("Error saving parking space: " + e.getMessage());
        }
    }

    @Override
    public ParkingSpace getById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    return parseParkingSpace(parts);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading parking space: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ParkingSpace> getAll() {
        List<ParkingSpace> spaces = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                spaces.add(parseParkingSpace(parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading parking spaces: " + e.getMessage());
        }
        return spaces;
    }

    @Override
    public void update(ParkingSpace space) {
        List<ParkingSpace> spaces = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (ParkingSpace s : spaces) {
                if (s.getId().equals(space.getId())) {
                    out.println(formatParkingSpace(space));
                } else {
                    out.println(formatParkingSpace(s));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating parking space: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        List<ParkingSpace> spaces = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (ParkingSpace space : spaces) {
                if (!space.getId().equals(id)) {
                    out.println(formatParkingSpace(space));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting parking space: " + e.getMessage());
        }
    }

    private String formatParkingSpace(ParkingSpace space) {
        return String.format("%s,%s,%s,%s,%s,%s",
                space.getId(),
                space.getLocation(),
                space.getType(),
                space.isEnabled(),
                space.getRate(),
                space.getState().getClass().getSimpleName());
    }

    private ParkingSpace parseParkingSpace(String[] parts) {
        String id = parts[0];
        String location = parts[1];
        String type = parts[2];
        boolean enabled = Boolean.parseBoolean(parts[3]);
        double rate = Double.parseDouble(parts[4]);
        String stateType = parts[5];

        ParkingSpace space = new ParkingSpace(id, location, type);
        space.setRate(rate);
        space.setEnabled(enabled);
        
        // State will be set by the ParkingSpace class based on enabled status
        return space;
    }
}