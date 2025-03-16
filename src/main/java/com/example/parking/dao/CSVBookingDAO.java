package com.example.parking.dao;

import com.example.parking.model.Booking;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVBookingDAO implements DAO<Booking> {

    private final String csvFilePath;

    public CSVBookingDAO(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public Booking getById(String id) {
        List<Booking> allBookings = getAll();
        for (Booking b : allBookings) {
            if (b.getBookingId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<Booking> getAll() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: bookingId,clientId,spaceId,startTime,endTime,totalCost
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String bookingId = parts[0].trim();
                    String clientId = parts[1].trim();
                    String spaceId = parts[2].trim();

                    LocalDateTime startTime = null;
                    LocalDateTime endTime = null;
                    double totalCost = 0.0;

                    // Parse startTime
                    try {
                        if(!parts[3].trim().isEmpty()) {
                            startTime = LocalDateTime.parse(parts[3].trim());
                        }
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }

                    // Parse endTime
                    try {
                        if(!parts[4].trim().isEmpty()) {
                            endTime = LocalDateTime.parse(parts[4].trim());
                        }
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }

                    // Parse totalCost
                    try {
                        totalCost = Double.parseDouble(parts[5].trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    Booking booking = new Booking(bookingId, clientId, spaceId, startTime);
                    booking.setEndTime(endTime);
                    booking.setTotalCost(totalCost);

                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void save(Booking obj) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // Convert startTime/endTime to string (ISO-8601 recommended)
            String startTimeStr = (obj.getStartTime() != null) ? obj.getStartTime().toString() : "";
            String endTimeStr = (obj.getEndTime() != null) ? obj.getEndTime().toString() : "";

            String line = obj.getBookingId() + "," +
                          obj.getClientId() + "," +
                          obj.getSpaceId() + "," +
                          startTimeStr + "," +
                          endTimeStr + "," +
                          obj.getTotalCost();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Booking updatedBooking) {
        List<Booking> allBookings = getAll();
        for (int i = 0; i < allBookings.size(); i++) {
            if (allBookings.get(i).getBookingId().equals(updatedBooking.getBookingId())) {
                allBookings.set(i, updatedBooking);
                break;
            }
        }
        overwriteCSV(allBookings);
    }

    @Override
    public void delete(String id) {
        List<Booking> allBookings = getAll();
        allBookings.removeIf(b -> b.getBookingId().equals(id));
        overwriteCSV(allBookings);
    }

    private void overwriteCSV(List<Booking> bookings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            for (Booking b : bookings) {
                String startTimeStr = (b.getStartTime() != null) ? b.getStartTime().toString() : "";
                String endTimeStr = (b.getEndTime() != null) ? b.getEndTime().toString() : "";

                String line = b.getBookingId() + "," +
                              b.getClientId() + "," +
                              b.getSpaceId() + "," +
                              startTimeStr + "," +
                              endTimeStr + "," +
                              b.getTotalCost();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}