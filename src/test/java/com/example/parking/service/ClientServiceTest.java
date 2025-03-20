package com.example.parking.service;

import com.example.parking.dao.CSVClientDAO;
import com.example.parking.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
// import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    private static final String TEST_CLIENTS_CSV = "data/test_clients.csv";
    private CSVClientDAO clientDAO;
    private ClientService clientService;

    @BeforeEach
    public void setUp() throws IOException {
        // Create or reset the test CSV file
        File testFile = new File(TEST_CLIENTS_CSV);
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.getParentFile().mkdirs();
        testFile.createNewFile();

        // Instantiate DAO and service
        clientDAO = new CSVClientDAO(TEST_CLIENTS_CSV);
        clientService = new ClientService(clientDAO);
    }

    @Test
    public void testRegisterClient_Success() {
        // Register a new client
        clientService.registerClient("C101", "Alice", "alice@example.com");
        
        // Verify the client is stored
        List<Client> allClients = clientService.getAllClients();
        assertEquals(1, allClients.size());
        assertEquals("C101", allClients.get(0).getClientId());
        assertEquals("Alice", allClients.get(0).getName());
        assertEquals("alice@example.com", allClients.get(0).getEmail());
    }

    @Test
    public void testRegisterClient_DuplicateId() {
        // First registration
        clientService.registerClient("C200", "Bob", "bob@example.com");

        // Second registration with same ID should throw
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            clientService.registerClient("C200", "Bobby", "bobby@example.com");
        });
        assertTrue(ex.getMessage().contains("already in use"));
    }

    @Test
    public void testUpdateClient() {
        // Register a client
        clientService.registerClient("C300", "Charlie", "charlie@example.com");
        
        // Retrieve and update
        Client existing = clientDAO.getById("C300");
        existing.setName("Charles");
        clientService.updateClient(existing);
        
        // Check changes
        Client updated = clientDAO.getById("C300");
        assertEquals("Charles", updated.getName());
    }

    @Test
    public void testDeleteClient() {
        // Register a client
        clientService.registerClient("C400", "Derek", "derek@example.com");
        assertEquals(1, clientService.getAllClients().size());

        // Delete the client
        clientService.deleteClient("C400");
        assertTrue(clientService.getAllClients().isEmpty());
    }
}