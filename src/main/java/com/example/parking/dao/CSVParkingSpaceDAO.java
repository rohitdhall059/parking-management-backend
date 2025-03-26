package com.example.parking.dao;

import com.example.parking.model.ParkingSpace;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParkingSpaceDAO implements DAO<ParkingSpace> {

    private String filePath;
    private List<ParkingSpace> parkingSpaces;

    public CSVParkingSpaceDAO(String filePath) {
        this.filePath = filePath;
        this.parkingSpaces = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    ParkingSpace space = new ParkingSpace(parts[0], parts[1], parts[2]);
                    parkingSpaces.add(space);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (ParkingSpace space : parkingSpaces) {
                writer.println(String.format("%s,%s,%s",
                    space.getSpaceId(),
                    space.getLocation(),
                    space.getType()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(ParkingSpace space) {
        parkingSpaces.add(space);
        saveToFile();
    }

    @Override
    public ParkingSpace getById(String id) {
        return parkingSpaces.stream()
                .filter(s -> s.getSpaceId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ParkingSpace> getAll() {
        return new ArrayList<>(parkingSpaces);
    }

    @Override
    public void update(ParkingSpace space) {
        for (int i = 0; i < parkingSpaces.size(); i++) {
            if (parkingSpaces.get(i).getSpaceId().equals(space.getSpaceId())) {
                parkingSpaces.set(i, space);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        parkingSpaces.removeIf(s -> s.getSpaceId().equals(id));
        saveToFile();
    }
}