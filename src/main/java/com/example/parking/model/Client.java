package com.example.parking.model;

public class Client {
    private PricingStrategy pricingStrategy;
    private String clientId;
    private String name;
    private String email;
    private boolean isRegistered;
    private String password; // Optional
    private Car car; // New field for the associated car

   // Constructor
    public Client(String clientId, String name, String email, String password, Car car, PricingStrategy pricingStrategy) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.isRegistered = false; // default
        this.password = password;  // if needed
        this.car = car; // Associate the car with the client
        this.pricingStrategy = pricingStrategy; // Set the pricing strategy
    }

    // Alternate constructor if you do NOT want a password
    public Client(String clientId, String name, String email, Car car, PricingStrategy pricingStrategy) {
        this(clientId, name, email, null, car, pricingStrategy);
    }


    // Getters and Setters
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

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Car getCar() {
        return car; // Getter for the car
    }

    public void setCar(Car car) {
        this.car = car; // Setter for the car
    }

    public double getParkingRate() {
        if (pricingStrategy != null) {
            return pricingStrategy.getRate(); // Get the rate from the pricing strategy
        }
        return 0.0; // Default rate if no strategy provided
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isRegistered=" + isRegistered +
                ", password=" + (password != null ? "******" : "null") +
                ", car=" + (car != null ? car.toString() : "No car associated") +
                ", pricingStrategy=" + (pricingStrategy != null ? pricingStrategy.getClass().getSimpleName() : "Not set") +
                '}';
    }
}