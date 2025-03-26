package com.example.parking.service;

import com.example.parking.dao.BookingDAO;
import com.example.parking.dao.ClientDAO;
import com.example.parking.dao.ParkingSpaceDAO;
import com.example.parking.model.Booking;
import com.example.parking.model.client.Client;
import com.example.parking.model.ParkingSpace;
import com.example.parking.model.payment.PaymentMethod;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * Manages booking creation, cancellation, etc.
 */
public class BookingService {

    private final BookingDAO bookingDAO;
    private final ClientDAO clientDAO;
    private final ParkingSpaceDAO parkingSpaceDAO;

    public BookingService(BookingDAO bookingDAO, ClientDAO clientDAO, ParkingSpaceDAO parkingSpaceDAO) {
        this.bookingDAO = bookingDAO;
        this.clientDAO = clientDAO;
        this.parkingSpaceDAO = parkingSpaceDAO;
    }

    /**
     * Creates a new booking if the space is free and the client is valid.
     * @param clientId  Must match an existing Client.
     * @param spaceId   Must match an existing ParkingSpace that is not occupied.
     * @param startTime Start of the booking.
     * @param endTime   End of the booking. Should be after startTime.
     * @param paymentMethod Type of the client.
     * @return the newly created Booking object, or throws exception if invalid.
     */
    public Booking createBooking(String clientId, String spaceId, LocalDateTime startTime, LocalDateTime endTime, PaymentMethod paymentMethod) {
        // Validate client
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found: " + clientId);
        }

        // Validate parking space
        ParkingSpace space = parkingSpaceDAO.getById(spaceId);
        if (space == null) {
            throw new IllegalArgumentException("Parking space not found: " + spaceId);
        }

        // Check if space is available
        if (space.isOccupied()) {
            throw new IllegalStateException("Parking space is already occupied: " + spaceId);
        }

        // Generate unique booking ID
        String bookingId = UUID.randomUUID().toString();

        // Calculate booking amount based on duration and space rate
        Duration duration = Duration.between(startTime, endTime);
        double hours = duration.toHours() + (duration.toMinutesPart() > 0 ? 1 : 0);
        double amount = hours * space.getRate();

        // Create booking
        Booking booking = new Booking(bookingId, client, space, startTime, endTime, paymentMethod);
        booking.setAmount(amount);
        booking.setStatus("CONFIRMED");

        // Save booking
        bookingDAO.save(booking);

        // Update parking space status
        space.setOccupied(true, client.getCar());
        parkingSpaceDAO.update(space);

        return booking;
    }

    /**
     * Cancels an existing booking (if it exists) and frees the parking space.
     */
    public void cancelBooking(String bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        // Update booking status
        booking.setStatus("CANCELLED");
        bookingDAO.update(booking);

        // Update parking space status
        ParkingSpace space = booking.getParkingSpace();
        space.setOccupied(false, null);
        parkingSpaceDAO.update(space);
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

    public void completeBooking(String bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        // Update booking status
        booking.setStatus("COMPLETED");
        bookingDAO.update(booking);

        // Update parking space status
        ParkingSpace space = booking.getParkingSpace();
        space.setOccupied(false, null);
        parkingSpaceDAO.update(space);
    }

    public List<Booking> getBookingsByClient(String clientId) {
        return bookingDAO.getByClientId(clientId);
    }

    public List<Booking> getBookingsBySpace(String spaceId) {
        return bookingDAO.getAll().stream()
                .filter(b -> b.getParkingSpace().getId().equals(spaceId))
                .toList();
    }
}