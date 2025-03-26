package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.Client;
import java.util.List;
import java.util.UUID;

/**
 * Handles all "client"-related business logic:
 *   - Register a new client (validations, etc.)
 *   - Fetch all clients, search by ID, etc.
 */
public class ClientService {

    private final DAO<Client> clientDAO;

    public ClientService(DAO<Client> clientDAO) {
        this.clientDAO = clientDAO;
    }

    /**
     * Registers a new client in the system.
     * @param name the client's name
     * @param email the client's email
     * @param password the client's password
     * @return the registered client
     * @throws IllegalArgumentException if the client with this email already exists
     */
    public Client registerClient(String name, String email, String password) {
        // Check if client already exists
        List<Client> existingClients = clientDAO.getAll();
        for (Client client : existingClients) {
            if (client.getEmail().equals(email)) {
                throw new IllegalArgumentException("Client with this email already exists");
            }
        }

        String clientId = UUID.randomUUID().toString();
        Client client = new Client(clientId, name, email, password, null, null);
        clientDAO.save(client);
        return client;
    }

    /**
     * Retrieves a client from the DAO by ID.
     * @param clientId the ID of the client to retrieve
     * @return the client with the specified ID
     */
    public Client getClient(String clientId) {
        return clientDAO.getById(clientId);
    }

    /**
     * Retrieves all clients from the DAO.
     * @return list of all clients.
     */
    public List<Client> getAllClients() {
        return clientDAO.getAll();
    }

    /**
     * Updates a client's registration status or other attributes.
     */
    public void updateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientDAO.update(client);
    }

    /**
     * Delete a client from the system, if needed.
     */
    public void deleteClient(String clientId) {
        clientDAO.delete(clientId);
    }

    /**
     * Logs in a client.
     * @param email the client's email
     * @param password the client's password
     * @return the logged-in client, or null if no client matches the provided email and password
     */
    public Client login(String email, String password) {
        List<Client> clients = clientDAO.getAll();
        return clients.stream()
                .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}