package com.example.parking.service;

import com.example.parking.dao.DAO;
import com.example.parking.model.Client;

import java.util.List;

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
     * @param client the client object to register
     * @throws IllegalArgumentException if the clientId already exists or data is invalid.
     */
    public void registerClient(Client client) {
        // 1) Validate input
        if (client == null) {
            throw new IllegalArgumentException("Client object cannot be null.");
        }
        if (client.getClientId() == null || client.getClientId().isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty.");
        }
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty.");
        }
        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Client email cannot be null or empty.");
        }

        // 2) Check if this ID already exists
        Client existing = clientDAO.getById(client.getClientId());
        if (existing != null) {
            throw new IllegalArgumentException("Client ID already in use: " + client.getClientId());
        }

        // 3) Set registration status
        client.setRegistered(true); // Assume newly registered

        // 4) Save via DAO
        clientDAO.save(client);
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
    public void updateClient(Client updatedClient) {
        if (updatedClient == null) {
            throw new IllegalArgumentException("Client object is null.");
        }
        Client existing = clientDAO.getById(updatedClient.getClientId());
        if (existing == null) {
            throw new IllegalArgumentException("No client found with ID: " + updatedClient.getClientId());
        }

        // Merge fields if needed, or just rely on updatedClient
        clientDAO.update(updatedClient);
    }

    /**
     * Delete a client from the system, if needed.
     */
    public void deleteClient(String clientId) {
        Client existing = clientDAO.getById(clientId);
        if (existing == null) {
            throw new IllegalArgumentException("No client found with ID: " + clientId);
        }
        clientDAO.delete(clientId);
    }
}