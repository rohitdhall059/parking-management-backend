package com.example.parking.dao;

import com.example.parking.model.ParkingSpace;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParkingSpaceDAO implements DAO<ParkingSpace> {

    private final String csvFilePath;

    public CSVParkingSpaceDAO(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public ParkingSpace getById(String id) {
        List<ParkingSpace> allSpaces = getAll();
        for (ParkingSpace space : allSpaces) {
            if (space.getSpaceId().equals(id)) {
                return space;
            }
        }
        return null; // not found
    }

    @Override
    public List<ParkingSpace> getAll() {
        List<ParkingSpace> spaces = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while((line = br.readLine()) != null) {
                // Format: spaceId,isOccupied,rate
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String spaceId = parts[0].trim();
                    boolean occupied = Boolean.parseBoolean(parts[1].trim());
                    double rate = Double.parseDouble(parts[2].trim());

                    ParkingSpace space = new ParkingSpace(spaceId, rate);
                    space.setOccupied(occupied);
                    spaces.add(space);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spaces;
    }

    @Override
    public void save(ParkingSpace obj) {
        // Append new ParkingSpace record to CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            String line = obj.getSpaceId() + "," +
                          obj.isOccupied() + "," +
                          obj.getRate();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ParkingSpace updatedSpace) {
        List<ParkingSpace> allSpaces = getAll();
        for (int i = 0; i < allSpaces.size(); i++) {
            if (allSpaces.get(i).getSpaceId().equals(updatedSpace.getSpaceId())) {
                allSpaces.set(i, updatedSpace);
                break;
            }
        }
        overwriteCSV(allSpaces);
    }

    @Override
    public void delete(String id) {
        List<ParkingSpace> allSpaces = getAll();
        allSpaces.removeIf(space -> space.getSpaceId().equals(id));
        overwriteCSV(allSpaces);
    }

    private void overwriteCSV(List<ParkingSpace> spaces) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            for (ParkingSpace space : spaces) {
                String line = space.getSpaceId() + "," +
                              space.isOccupied() + "," +
                              space.getRate();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}