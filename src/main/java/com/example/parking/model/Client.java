package com.example.parking.model;

import com.example.parking.strategy.ParkingRateStrategy;
import java.util.List;
import java.util.ArrayList;

public abstract class Client {
    protected String email;
    protected String password;
    protected String name;
    protected ParkingRateStrategy parkingRateStrategy;
    protected List<Booking> bookings;
    protected PaymentMethod paymentMethod;

    public Client(String email, String password, String name, ParkingRateStrategy parkingRateStrategy) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.parkingRateStrategy = parkingRateStrategy;
        this.bookings = new ArrayList<>();
    }

    public boolean validateLogin(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void createBooking(ParkingSpace space, String licensePlate, String fromTime, String toTime) {
        Booking booking = new Booking(space, licensePlate, fromTime, toTime);
        bookings.add(booking);
    }

    public void cancelBooking(Booking booking) {
        if (bookings.contains(booking)) {
            booking.cancel();
            bookings.remove(booking);
        }
    }

    public double calculateParkingRate(int hours) {
        return parkingRateStrategy.calculateRate(hours);
    }

    public void setParkingRateStrategy(ParkingRateStrategy strategy) {
        this.parkingRateStrategy = strategy;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void processPayment(double amount) {
        if (paymentMethod != null) {
            paymentMethod.processPayment(amount);
        }
    }

    // Getters
    public String getEmail() { return email; }
    public String getName() { return name; }
    public List<Booking> getBookings() { return bookings; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
}