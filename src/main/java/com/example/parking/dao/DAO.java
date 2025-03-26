package com.example.parking.dao;

import java.util.List;

public interface DAO<T> {
    void save(T item);
    T getById(String id);
    List<T> getAll();
    void update(T item);
    void delete(String id);
}