package com.example.parking.dao;

import com.example.parking.model.Booking;
import java.util.List;

public interface BookingDAO {
    void save(Booking booking);
    Booking getById(String id);
    List<Booking> getAll();
    void update(Booking booking);
    void delete(String id);
    List<Booking> getByClientId(String clientId);
} 