package com.example.parking.service;

import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.model.Booking;
import com.example.parking.model.Client;
import com.example.parking.model.ParkingSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
// import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private static final String TEST_BOOKINGS = "data/test_bookings.csv";
    private static final String TEST_CLIENTS = "data/test_clients.csv";
    private static final String TEST_SPACES = "data/test_spaces.csv";

    private CSVBookingDAO bookingDAO;
    private CSVClientDAO clientDAO;
    private CSVParkingSpaceDAO spaceDAO;
    private BookingService bookingService;

    @BeforeEach
    public void setUp() throws IOException {
        resetFile(TEST_BOOKINGS);
        resetFile(TEST_CLIENTS);
        resetFile(TEST_SPACES);

        bookingDAO = new CSVBookingDAO(TEST_BOOKINGS);
        clientDAO = new CSVClientDAO(TEST_CLIENTS);
        spaceDAO = new CSVParkingSpaceDAO(TEST_SPACES);

        // Preload a client and space
        Client c = new Client("C100", "Eve", "eve@example.com");
        c.setRegistered(true);
        clientDAO.save(c);

        ParkingSpace ps = new ParkingSpace("SP100", 3.0);
        ps.setOccupied(false);
        spaceDAO.save(ps);

        bookingService = new BookingService(bookingDAO, clientDAO, spaceDAO);
    }

    @Test
    public void testCreateBooking_Success() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 1, 12, 0);
        Booking b = bookingService.createBooking("BK100", "C100", "SP100", start, end);
        assertNotNull(b);
        assertEquals("BK100", b.getBookingId());

        ParkingSpace sp = spaceDAO.getById("SP100");
        assertTrue(sp.isOccupied());
    }

    @Test
    public void testCreateBooking_AlreadyOccupied() {
        // Occupy the space first
        bookingService.createBooking("BK200", "C100", "SP100",
                LocalDateTime.of(2025, 4, 1, 9, 0),
                LocalDateTime.of(2025, 4, 1, 10, 0));

        // Attempt a second booking
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            bookingService.createBooking("BK201", "C100", "SP100",
                    LocalDateTime.of(2025, 4, 1, 9, 30),
                    LocalDateTime.of(2025, 4, 1, 10, 30));
        });
        assertTrue(ex.getMessage().contains("occupied"));
    }

    @Test
    public void testCancelBooking() {
        bookingService.createBooking("BK300", "C100", "SP100",
                LocalDateTime.of(2025, 5, 1, 9, 0),
                LocalDateTime.of(2025, 5, 1, 10, 0));
        bookingService.cancelBooking("BK300");

        assertNull(bookingDAO.getById("BK300"));
        assertFalse(spaceDAO.getById("SP100").isOccupied());
    }

    private void resetFile(String path) throws IOException {
        File f = new File(path);
        if (f.exists()) f.delete();
        f.getParentFile().mkdirs();
        f.createNewFile();
    }
}
