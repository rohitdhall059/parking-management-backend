package com.example.parking.dao;

import com.example.parking.model.Booking;
import com.example.parking.model.client.Client;
import com.example.parking.model.ParkingSpace;
import com.example.parking.model.payment.PaymentMethod;
import com.example.parking.factory.PaymentMethodFactory;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages booking data persistence using CSV file storage.
 * Handles all booking-related CRUD operations and maintains relationships
 * with clients and parking spaces.
 */
public class CSVBookingDAO implements BookingDAO {
    private final String filePath;
    private final ClientDAO clientDAO;
    private final ParkingSpaceDAO parkingSpaceDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CSVBookingDAO(ClientDAO clientDAO, ParkingSpaceDAO parkingSpaceDAO) {
        this.filePath = "bookings.csv";
        this.clientDAO = clientDAO;
        this.parkingSpaceDAO = parkingSpaceDAO;
    }

    /**
     * Saves a new booking record to the CSV file.
     * Appends the record to the end of the file.
     */
    @Override
    public void save(Booking booking) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(formatBooking(booking));
        } catch (IOException e) {
            throw new RuntimeException("Error saving booking: " + e.getMessage());
        }
    }

    /**
     * Retrieves a booking by its unique ID.
     * Returns null if no booking is found with the given ID.
     */
    @Override
    public Booking getById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    return parseBooking(parts);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading booking: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all booking records from the CSV file.
     */
    @Override
    public List<Booking> getAll() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                bookings.add(parseBooking(parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Updates an existing booking record in the CSV file.
     * Replaces the entire file content to update the record.
     */
    @Override
    public void update(Booking booking) {
        List<Booking> bookings = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Booking b : bookings) {
                if (b.getId().equals(booking.getId())) {
                    out.println(formatBooking(booking));
                } else {
                    out.println(formatBooking(b));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating booking: " + e.getMessage());
        }
    }

    /**
     * Removes a booking record from the CSV file.
     * Recreates the file without the deleted record.
     */
    @Override
    public void delete(String id) {
        List<Booking> bookings = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Booking booking : bookings) {
                if (!booking.getId().equals(id)) {
                    out.println(formatBooking(booking));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting booking: " + e.getMessage());
        }
    }

    /**
     * Retrieves all bookings associated with a specific client.
     */
    @Override
    public List<Booking> getByClientId(String clientId) {
        return getAll().stream()
                .filter(b -> b.getClient().getId().equals(clientId))
                .toList();
    }

    /**
     * Formats a booking object into a CSV string.
     * Includes all booking details including client ID, parking space ID,
     * timestamps, payment information, and status.
     */
    private String formatBooking(Booking booking) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                booking.getId(),
                booking.getClient().getId(),
                booking.getParkingSpace().getId(),
                booking.getStartTime().format(formatter),
                booking.getEndTime().format(formatter),
                booking.getPaymentMethod().getType(),
                booking.getPaymentMethod().getCardNumber(),
                booking.getPaymentMethod().getCredential(),
                booking.getStatus());
    }

    /**
     * Parses a CSV line into a Booking object.
     * Reconstructs related objects (Client, ParkingSpace, PaymentMethod)
     * and calculates the final booking amount.
     */
    private Booking parseBooking(String[] parts) {
        String id = parts[0];
        Client client = clientDAO.getById(parts[1]);
        ParkingSpace space = parkingSpaceDAO.getById(parts[2]);
        LocalDateTime startTime = LocalDateTime.parse(parts[3], formatter);
        LocalDateTime endTime = LocalDateTime.parse(parts[4], formatter);
        PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(
                parts[5], parts[6], parts[7]);
        
        Booking booking = new Booking(id, client, space, startTime, endTime, paymentMethod);
        booking.setStatus(parts[8]);
        
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        double baseAmount = hours * space.getRate();
        double discountRate = client.getDiscountRate();
        double finalAmount = baseAmount * (1 - discountRate);
        booking.setAmount(finalAmount);
        
        return booking;
    }
}