package com.example.parking.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingId;
    private String clientId; // or hold a Client reference
    private String spaceId;  // or hold a ParkingSpace reference
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private PaymentMethod paymentMethod;
    private ParkingSpace space;
    private String licensePlate;
    private boolean isCancelled;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Booking(String bookingId, String clientId, String spaceId, LocalDateTime startTime, LocalDateTime endTime, double totalCost) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.spaceId = spaceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.isCancelled = false;
    }

    public Booking(ParkingSpace space, String licensePlate, String fromTime, String toTime) {
        this.space = space;
        this.licensePlate = licensePlate;
        this.startTime = LocalDateTime.parse(fromTime, formatter);
        this.endTime = LocalDateTime.parse(toTime, formatter);
        this.isCancelled = false;
    }

    // Getters/Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public ParkingSpace getSpace() { return space; }
    public String getLicensePlate() { return licensePlate; }
    public boolean isCancelled() { return isCancelled; }

    @Override
    public String toString() {
        return "Booking{" +
                "space=" + (space != null ? space.getId() : spaceId) +
                ", licensePlate='" + licensePlate + '\'' +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + endTime.format(formatter) +
                ", status=" + (isCancelled ? "Cancelled" : isActive() ? "Active" : isExpired() ? "Expired" : "Scheduled") +
                '}';
    }

    // Method to create a booking
    public static Booking createBooking(String bookingId, String clientId, String spaceId, LocalDateTime startTime, LocalDateTime endTime, String clientType) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        PricingStrategy pricingStrategy = PricingStrategyFactory.getPricingStrategy(clientType);
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        double totalCost = hours * pricingStrategy.getRate();
        return new Booking(bookingId, clientId, spaceId, startTime, endTime, totalCost);
    }

    // Method to checkout
    public void checkout() {
        System.out.println("Checking out booking: " + bookingId);
        // Logic to finalize the booking, e.g., mark the parking space as free
        // Deduct the deposit from the total cost
        totalCost -= deposit;
        System.out.println("Total cost after deducting deposit: " + totalCost);
    }

    // Method to extend parking time
    public void extend(String newEndTime) {
        if (isCancelled) {
            throw new IllegalStateException("Cannot extend a cancelled booking");
        }
        LocalDateTime newEnd = LocalDateTime.parse(newEndTime, formatter);
        if (newEnd.isBefore(endTime)) {
            throw new IllegalArgumentException("New end time must be after current end time");
        }
        
        // Calculate additional cost if needed
        long additionalHours = ChronoUnit.HOURS.between(endTime, newEnd);
        if (additionalHours > 0) {
            double additionalCost = calculateAdditionalCost(additionalHours);
            this.totalCost += additionalCost;
        }
        
        this.endTime = newEnd;
    }

    public void cancel() {
        if (!isCancelled) {
            isCancelled = true;
            if (space != null) {
                space.vacate();
            }
            
            // Process refund if applicable
            double refundAmount = calculateRefund();
            if (refundAmount > 0 && paymentMethod != null) {
                processRefund(refundAmount);
            }
        }
    }

    private double calculateAdditionalCost(long hours) {
        // Implement cost calculation logic here
        return hours * (totalCost / ChronoUnit.HOURS.between(startTime, endTime));
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return !isCancelled && now.isAfter(startTime) && now.isBefore(endTime);
    }

    public boolean isExpired() {
        return !isCancelled && LocalDateTime.now().isAfter(endTime);
    }

    public double calculateRefund() {
        if (isCancelled || isExpired()) {
            return 0.0;
        }
        
        long minutesLeft = ChronoUnit.MINUTES.between(LocalDateTime.now(), endTime);
        if (minutesLeft > 0) {
            long totalMinutes = ChronoUnit.MINUTES.between(startTime, endTime);
            return (totalCost / totalMinutes) * minutesLeft;
        }
        return 0.0;
    }

    public void processPayment(double amount) {
        if (paymentMethod != null) {
            paymentMethod.processPayment(amount);
        } else {
            throw new IllegalStateException("No payment method set for this booking");
        }
    }

    public void processRefund(double amount) {
        if (paymentMethod != null) {
            paymentMethod.processRefund(amount);
        } else {
            throw new IllegalStateException("No payment method set for this booking");
        }
    }
}