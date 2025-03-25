package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.Booking;
import com.example.parking.model.Client;
import com.example.parking.model.ParkingSpace;
import com.example.parking.model.PricingStrategy;
import com.example.parking.model.PricingStrategyFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * Manages booking creation, cancellation, etc.
 */
public class BookingService {

    private final DAO<Booking> bookingDAO;
    private final DAO<Client> clientDAO;
    private final DAO<ParkingSpace> spaceDAO;

    public BookingService(DAO<Booking> bookingDAO,
                          DAO<Client> clientDAO,
                          DAO<ParkingSpace> spaceDAO) {
        this.bookingDAO = bookingDAO;
        this.clientDAO = clientDAO;
        this.spaceDAO = spaceDAO;
    }

    /**
     * Creates a new booking if the space is free and the client is valid.
     * @param clientId  Must match an existing Client.
     * @param spaceId   Must match an existing ParkingSpace that is not occupied.
     * @param startTime Start of the booking.
     * @param endTime   End of the booking. Should be after startTime.
     * @param clientType Type of the client.
     * @return the newly created Booking object, or throws exception if invalid.
     */
    public Booking createBooking(String clientId, String spaceId, LocalDateTime startTime, LocalDateTime endTime, String clientType) {
        // Validate inputs
        if (clientId == null || spaceId == null || startTime == null || endTime == null || clientType == null) {
            throw new IllegalArgumentException("All parameters must be non-null");
        }

        // Check if space exists and is available
        ParkingSpace space = spaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found");
        }
        if (space.isOccupied()) {
            throw new IllegalStateException("Parking space is already occupied");
        }

        // Check if client exists
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        // Generate booking ID
        String bookingId = UUID.randomUUID().toString();

        // Calculate total cost
        PricingStrategy pricingStrategy = PricingStrategyFactory.getPricingStrategy(clientType);
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        double totalCost = hours * pricingStrategy.getRate();

        // Create booking
        Booking newBooking = new Booking(bookingId, clientId, spaceId, startTime, endTime, totalCost);

        // Update parking space status
        space.setOccupied(true, null);

        // Save booking
        bookingDAO.save(newBooking);

        return newBooking;
    }

    /**
     * Cancels an existing booking (if it exists) and frees the parking space.
     */
    public void cancelBooking(String bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }

        // Update parking space status
        ParkingSpace space = spaceDAO.getById(booking.getSpaceId());
        if (space != null) {
            space.setOccupied(false, null);
        }

        // Process refund if applicable
        double refundAmount = booking.calculateRefund();
        if (refundAmount > 0) {
            booking.processRefund(refundAmount);
        }

        // Delete booking
        bookingDAO.delete(bookingId);
    }

    /**
     * Retrieve all bookings if the GUI needs a list.
     */
    public List<Booking> getAllBookings() {
        return bookingDAO.getAll();
    }

    public Booking getBooking(String bookingId) {
        return bookingDAO.getById(bookingId);
    }
}