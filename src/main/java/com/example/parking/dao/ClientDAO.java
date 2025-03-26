package com.example.parking.dao;

import com.example.parking.model.client.Client;
import java.util.List;

public interface ClientDAO {
    void save(Client client);
    Client getById(String id);
    List<Client> getAll();
    void update(Client client);
    void delete(String id);
} 