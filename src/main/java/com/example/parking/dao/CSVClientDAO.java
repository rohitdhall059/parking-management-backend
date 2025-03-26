package com.example.parking.dao;

import com.example.parking.model.client.Client;
import com.example.parking.model.car.Car;
import com.example.parking.factory.ClientFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles client data persistence using CSV file storage.
 * Provides CRUD operations for client records.
 */
public class CSVClientDAO implements ClientDAO {
    private final String filePath;

    public CSVClientDAO() {
        this.filePath = "clients.csv";
    }

    /**
     * Saves a new client record to the CSV file.
     * Appends the record to the end of the file.
     */
    @Override
    public void save(Client client) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(formatClient(client));
        } catch (IOException e) {
            throw new RuntimeException("Error saving client: " + e.getMessage());
        }
    }

    /**
     * Retrieves a client by their unique ID.
     * Returns null if no client is found with the given ID.
     */
    @Override
    public Client getById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    return parseClient(parts);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading client: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all client records from the CSV file.
     */
    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                clients.add(parseClient(parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading clients: " + e.getMessage());
        }
        return clients;
    }

    /**
     * Updates an existing client record in the CSV file.
     * Replaces the entire file content to update the record.
     */
    @Override
    public void update(Client client) {
        List<Client> clients = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Client c : clients) {
                if (c.getId().equals(client.getId())) {
                    out.println(formatClient(client));
                } else {
                    out.println(formatClient(c));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating client: " + e.getMessage());
        }
    }

    /**
     * Removes a client record from the CSV file.
     * Recreates the file without the deleted record.
     */
    @Override
    public void delete(String id) {
        List<Client> clients = getAll();
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Client client : clients) {
                if (!client.getId().equals(id)) {
                    out.println(formatClient(client));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting client: " + e.getMessage());
        }
    }

    /**
     * Formats a client object into a CSV string.
     * Includes client ID, name, email, status, and car license plate.
     */
    private String formatClient(Client client) {
        return String.format("%s,%s,%s,%s,%s",
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getStatus(),
                client.getCar() != null ? client.getCar().getLicensePlate() : "");
    }

    /**
     * Parses a CSV line into a Client object.
     * Creates the appropriate client type based on ID prefix.
     */
    private Client parseClient(String[] parts) {
        String id = parts[0];
        String name = parts[1];
        String email = parts[2];
        String status = parts[3];
        String carPlate = parts[4];

        String type = id.substring(0, 2).toUpperCase();
        Client client = ClientFactory.createClient(type, id, name, email);
        client.setStatus(status);
        
        if (carPlate != null && !carPlate.isEmpty()) {
            Car car = new Car(carPlate);
            client.setCar(car);
        }
        
        return client;
    }
}