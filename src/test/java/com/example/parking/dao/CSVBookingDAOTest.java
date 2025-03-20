package com.example.parking.dao;

import com.example.parking.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVBookingDAOTest {

    private static final String TEST_BOOKINGS_CSV = "data/test_bookings.csv";
    private CSVBookingDAO bookingDAO;

    @BeforeEach
    public void setUp() throws IOException {
        File f = new File(TEST_BOOKINGS_CSV);
        if (f.exists()) f.delete();
        f.getParentFile().mkdirs();
        f.createNewFile();

        bookingDAO = new CSVBookingDAO(TEST_BOOKINGS_CSV);
    }

    @Test
    public void testSaveAndGetAll() {
        Booking b1 = new Booking("BK100", "C100", "SP100", LocalDateTime.now());
        Booking b2 = new Booking("BK101", "C101", "SP101", LocalDateTime.now());
        bookingDAO.save(b1);
        bookingDAO.save(b2);

        List<Booking> all = bookingDAO.getAll();
        assertEquals(2, all.size());
        assertEquals("BK100", all.get(0).getBookingId());
        assertEquals("BK101", all.get(1).getBookingId());
    }

    @Test
    public void testGetById() {
        bookingDAO.save(new Booking("BK200", "C200", "SP200", LocalDateTime.now()));
        Booking found = bookingDAO.getById("BK200");
        assertNotNull(found);
        assertEquals("C200", found.getClientId());
    }

    @Test
    public void testUpdate() {
        Booking b = new Booking("BK300", "C300", "SP300", LocalDateTime.now());
        bookingDAO.save(b);

        b.setTotalCost(50.0);
        bookingDAO.update(b);

        Booking updated = bookingDAO.getById("BK300");
        assertEquals(50.0, updated.getTotalCost());
    }

    @Test
    public void testDelete() {
        bookingDAO.save(new Booking("BK400", "C400", "SP400", LocalDateTime.now()));
        bookingDAO.delete("BK400");
        assertNull(bookingDAO.getById("BK400"));
    }
}
