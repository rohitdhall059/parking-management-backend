package com.example.parking.dao;

import com.example.parking.model.ParkingSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParkingSpaceDAOTest {

    private static final String TEST_SPACES_CSV = "data/test_spaces.csv";
    private CSVParkingSpaceDAO spaceDAO;

    @BeforeEach
    public void setUp() throws IOException {
        File f = new File(TEST_SPACES_CSV);
        if (f.exists()) f.delete();
        f.getParentFile().mkdirs();
        f.createNewFile();

        spaceDAO = new CSVParkingSpaceDAO(TEST_SPACES_CSV);
    }

    @Test
    public void testSaveAndGetAll() {
        ParkingSpace ps1 = new ParkingSpace("SP100", 3.5);
        ParkingSpace ps2 = new ParkingSpace("SP101", 4.0);
        spaceDAO.save(ps1);
        spaceDAO.save(ps2);

        List<ParkingSpace> all = spaceDAO.getAll();
        assertEquals(2, all.size());
        assertEquals("SP100", all.get(0).getSpaceId());
        assertEquals("SP101", all.get(1).getSpaceId());
    }

    @Test
    public void testGetById() {
        spaceDAO.save(new ParkingSpace("SP200", 2.5));
        ParkingSpace found = spaceDAO.getById("SP200");
        assertNotNull(found);
        assertEquals(2.5, found.getRate());
    }

    @Test
    public void testUpdate() {
        ParkingSpace ps = new ParkingSpace("SP300", 3.0);
        spaceDAO.save(ps);

        ps.setRate(3.5);
        spaceDAO.update(ps);

        ParkingSpace updated = spaceDAO.getById("SP300");
        assertEquals(3.5, updated.getRate());
    }

    @Test
    public void testDelete() {
        spaceDAO.save(new ParkingSpace("SP400", 4.5));
        spaceDAO.delete("SP400");
        assertNull(spaceDAO.getById("SP400"));
    }
}