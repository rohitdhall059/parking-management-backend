package com.example.parking.model;

import com.example.parking.model.car.Car;

public class Client {
    private String clientId;
    private String name;
    private String email;
    private String password;
    private Car car;
    private String type;

    public Client(String clientId, String name, String email, String password, Car car, String type) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.car = car;
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 