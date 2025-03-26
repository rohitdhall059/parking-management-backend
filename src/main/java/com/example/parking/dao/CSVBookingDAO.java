package com.example.parking.dao;

import com.example.parking.model.Booking;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVBookingDAO implements DAO<Booking> {

    private String filePath;
    private List<Booking> bookings;

    public CSVBookingDAO(String filePath) {
        this.filePath = filePath;
        this.bookings = new ArrayList<>();
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
                if (parts.length >= 4) {
                    Booking booking = new Booking(parts[0], parts[1], parts[2], parts[3]);
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Booking booking : bookings) {
                writer.println(String.format("%s,%s,%s,%s",
                    booking.getBookingId(),
                    booking.getClientId(),
                    booking.getSpaceId(),
                    booking.getStatus()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Booking booking) {
        bookings.add(booking);
        saveToFile();
    }

    @Override
    public Booking getById(String id) {
        return bookings.stream()
                .filter(b -> b.getBookingId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Booking> getAll() {
        return new ArrayList<>(bookings);
    }

    @Override
    public void update(Booking booking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().equals(booking.getBookingId())) {
                bookings.set(i, booking);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        bookings.removeIf(b -> b.getBookingId().equals(id));
        saveToFile();
    }
}