package com.example.parking.dao;

import com.example.parking.model.ParkingSpace;
import java.util.List;

public interface ParkingSpaceDAO {
    void save(ParkingSpace space);
    ParkingSpace getById(String id);
    List<ParkingSpace> getAll();
    void update(ParkingSpace space);
    void delete(String id);
} 