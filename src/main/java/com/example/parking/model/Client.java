package com.example.parking.model;

import com.example.parking.model.car.Car;

public abstract class Client {
    private final String id;
    private final String name;
    private final String email;
    private String status;
    private Car car;

    protected Client(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = "ACTIVE";
        this.car = null;
    }

    public Client(String clientId, String name2, String email2, String password, Object object, String string) {
        //TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public abstract double getDiscountRate();
} 