package com.example.parking.dao;

import com.example.parking.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVClientDAO implements DAO<Client> {

    private final String csvFilePath;
    private List<Client> clients;

    public CSVClientDAO(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.clients = new ArrayList<>();
        loadFromFile();
        // Create the directory if it doesn't exist
        File file = new File(csvFilePath);
        file.getParentFile().mkdirs();
    }

    private void loadFromFile() {
        File file = new File(csvFilePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Client client = new Client(parts[0], parts[1], parts[2], null, null, null);
                    clients.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            for (Client client : clients) {
                writer.println(String.format("%s,%s,%s",
                    client.getClientId(),
                    client.getName(),
                    client.getEmail()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getById(String id) {
        return clients.stream()
                .filter(c -> c.getClientId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public void save(Client client) {
        clients.add(client);
        saveToFile();
    }

    @Override
    public void update(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientId().equals(client.getClientId())) {
                clients.set(i, client);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        clients.removeIf(c -> c.getClientId().equals(id));
        saveToFile();
    }
}