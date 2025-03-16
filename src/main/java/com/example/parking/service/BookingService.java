package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.Booking;
import com.example.parking.model.Client;
import com.example.parking.model.ParkingSpace;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param bookingId Unique ID for the booking.
     * @param clientId  Must match an existing Client.
     * @param spaceId   Must match an existing ParkingSpace that is not occupied.
     * @param startTime Start of the booking.
     * @param endTime   End of the booking. Should be after startTime.
     * @return the newly created Booking object, or throws exception if invalid.
     */
    public Booking createBooking(String bookingId,
                                 String clientId,
                                 String spaceId,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime) {
        // 1) Validate basic input
        if (bookingId == null || bookingId.isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null/empty.");
        }
        if (startTime == null || endTime == null || !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("Invalid start/end time.");
        }

        // 2) Check if booking already exists
        Booking existingBooking = bookingDAO.getById(bookingId);
        if (existingBooking != null) {
            throw new IllegalArgumentException("Booking ID already in use: " + bookingId);
        }

        // 3) Check Client
        Client client = clientDAO.getById(clientId);
        if (client == null || !client.isRegistered()) {
            throw new IllegalStateException("Client " + clientId + " not found or not registered.");
        }

        // 4) Check ParkingSpace
        ParkingSpace space = spaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("No parking space with ID: " + spaceId);
        }
        if (space.isOccupied()) {
            throw new IllegalStateException("Space " + spaceId + " is already occupied.");
        }

        // 5) Calculate cost (simple example: rate * hours)
        long hours = java.time.Duration.between(startTime, endTime).toHours();
        if (hours <= 0) {
            hours = 1; // minimum 1 hour, for example
        }
        double totalCost = space.getRate() * hours;

        // 6) Create the Booking object
        Booking newBooking = new Booking(bookingId, clientId, spaceId, startTime);
        newBooking.setEndTime(endTime);
        newBooking.setTotalCost(totalCost);

        // 7) Save booking
        bookingDAO.save(newBooking);

        // 8) Mark space as occupied
        space.setOccupied(true);
        spaceDAO.update(space);

        // 9) Return the new booking
        return newBooking;
    }

    /**
     * Cancels an existing booking (if it exists) and frees the parking space.
     */
    public void cancelBooking(String bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("No booking found with ID: " + bookingId);
        }
        // Freed the space
        ParkingSpace space = spaceDAO.getById(booking.getSpaceId());
        if (space != null && space.isOccupied()) {
            space.setOccupied(false);
            spaceDAO.update(space);
        }
        // Remove booking record
        bookingDAO.delete(bookingId);
    }

    /**
     * Retrieve all bookings if the GUI needs a list.
     */
    public List<Booking> getAllBookings() {
        return bookingDAO.getAll();
    }
}