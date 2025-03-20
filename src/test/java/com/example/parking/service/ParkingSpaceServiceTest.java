package com.example.parking.service;

import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.model.ParkingSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpaceServiceTest {

    private static final String TEST_SPACES = "data/test_spaces.csv";
    private CSVParkingSpaceDAO spaceDAO;
    private ParkingSpaceService spaceService;

    @BeforeEach
    public void setUp() throws IOException {
        File f = new File(TEST_SPACES);
        if (f.exists()) f.delete();
        f.getParentFile().mkdirs();
        f.createNewFile();

        spaceDAO = new CSVParkingSpaceDAO(TEST_SPACES);
        spaceService = new ParkingSpaceService(spaceDAO);
    }

    @Test
    public void testAddSpaceAndListAvailable() {
        spaceDAO.save(new ParkingSpace("SP200", 4.5));
        List<ParkingSpace> available = spaceService.getAvailableSpaces();
        assertEquals(1, available.size());
        assertEquals("SP200", available.get(0).getSpaceId());
    }

    @Test
    public void testMarkSpaceOccupied() {
        spaceDAO.save(new ParkingSpace("SP300", 3.0));
        spaceService.markSpaceOccupied("SP300");
        assertTrue(spaceDAO.getById("SP300").isOccupied());
    }

    @Test
    public void testMarkSpaceFree() {
        ParkingSpace ps = new ParkingSpace("SP400", 2.5);
        ps.setOccupied(true);
        spaceDAO.save(ps);

        spaceService.markSpaceFree("SP400");
        assertFalse(spaceDAO.getById("SP400").isOccupied());
    }
}
