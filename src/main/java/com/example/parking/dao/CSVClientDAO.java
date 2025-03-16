package com.example.parking.dao;

import com.example.parking.model.Client;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVClientDAO implements DAO<Client> {

    private final String csvFilePath;

    public CSVClientDAO(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public Client getById(String id) {
        List<Client> allClients = getAll();
        for(Client client : allClients) {
            if(client.getClientId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while((line = br.readLine()) != null) {
                // CSV line format: clientId,name,email,isRegistered
                String[] parts = line.split(",");
                if(parts.length >= 4) {
                    String clientId = parts[0].trim();
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    boolean registered = Boolean.parseBoolean(parts[3].trim());

                    Client client = new Client(clientId, name, email);
                    client.setRegistered(registered);
                    clients.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void save(Client obj) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // append a new line to the CSV
            String line = obj.getClientId() + "," +
                          obj.getName() + "," +
                          obj.getEmail() + "," +
                          obj.isRegistered();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client updatedClient) {
        // 1. Read all existing clients
        List<Client> allClients = getAll();

        // 2. Replace the matching client
        for(int i = 0; i < allClients.size(); i++) {
            if(allClients.get(i).getClientId().equals(updatedClient.getClientId())) {
                allClients.set(i, updatedClient);
                break;
            }
        }

        // 3. Overwrite the entire CSV
        overwriteCSV(allClients);
    }

    @Override
    public void delete(String id) {
        List<Client> allClients = getAll();
        allClients.removeIf(client -> client.getClientId().equals(id));
        overwriteCSV(allClients);
    }

    private void overwriteCSV(List<Client> clients) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            for(Client c : clients) {
                String line = c.getClientId() + "," +
                              c.getName() + "," +
                              c.getEmail() + "," +
                              c.isRegistered();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}