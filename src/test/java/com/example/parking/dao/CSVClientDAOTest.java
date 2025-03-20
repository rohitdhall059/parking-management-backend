package com.example.parking.dao;

import com.example.parking.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVClientDAOTest {

    private static final String TEST_CLIENTS_CSV = "data/test_clients.csv";
    private CSVClientDAO clientDAO;

    @BeforeEach
    public void setUp() throws IOException {
        File testFile = new File(TEST_CLIENTS_CSV);
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.getParentFile().mkdirs();
        testFile.createNewFile();

        clientDAO = new CSVClientDAO(TEST_CLIENTS_CSV);
    }

    @Test
    public void testSaveAndGetAll() {
        Client c1 = new Client("C100", "Alice", "alice@example.com");
        Client c2 = new Client("C101", "Bob", "bob@example.com");
        clientDAO.save(c1);
        clientDAO.save(c2);

        List<Client> clients = clientDAO.getAll();
        assertEquals(2, clients.size());
        assertEquals("C100", clients.get(0).getClientId());
        assertEquals("C101", clients.get(1).getClientId());
    }

    @Test
    public void testGetById() {
        clientDAO.save(new Client("C200", "Charlie", "charlie@example.com"));
        Client found = clientDAO.getById("C200");
        assertNotNull(found);
        assertEquals("Charlie", found.getName());
    }

    @Test
    public void testUpdate() {
        Client c = new Client("C300", "David", "david@example.com");
        clientDAO.save(c);

        c.setName("Dave");
        clientDAO.update(c);

        Client updated = clientDAO.getById("C300");
        assertEquals("Dave", updated.getName());
    }

    @Test
    public void testDelete() {
        Client c = new Client("C400", "Eve", "eve@example.com");
        clientDAO.save(c);

        clientDAO.delete("C400");
        assertNull(clientDAO.getById("C400"));
    }
}
