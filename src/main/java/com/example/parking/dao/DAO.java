package com.example.parking.dao;

import java.util.List;

public interface DAO<T> {
    T getById(String id);
    List<T> getAll();
    void save(T obj);
    void update(T obj);
    void delete(String id);
}