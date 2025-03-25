package com.example.parking.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private String bookingId;
    private String clientId; // or hold a Client reference
    private String spaceId;  // or hold a ParkingSpace reference
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private PaymentMethod paymentMethod;
    private double deposit = 10.0; // Default deposit amount

    public Booking(String bookingId, String clientId, String spaceId, LocalDateTime startTime, LocalDateTime endTime, double totalCost) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.spaceId = spaceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
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

    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }

    @Override
    public String toString() {
        return "Booking {" + "bookingID = '" + bookingId + '\'' + ", clientID = '" + clientId + '\'' + ", spaceID = '" + spaceId + '\'' + ", startTime = " + startTime + ", endTime = " + endTime + ", totalCost = " + totalCost + '}';
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
        totalCost -= deposit;
        System.out.println("Total cost after deducting deposit: " + totalCost);
    }

    // Method to calculate refund based on time remaining
    public double calculateRefund() {
        long minutesLeft = ChronoUnit.MINUTES.between(LocalDateTime.now(), endTime);
        if (minutesLeft > 0) {
            double refundAmount = (totalCost / ChronoUnit.MINUTES.between(startTime, endTime)) * minutesLeft;
            return refundAmount;
        }
        return 0.0;
    }

    // Method to extend parking time
    public void extendParkingTime(LocalDateTime newEndTime, String clientType) {
        if (newEndTime.isBefore(endTime)) {
            throw new IllegalArgumentException("New end time must be after the current end time.");
        }

        // Calculate additional cost
        long additionalHours = ChronoUnit.HOURS.between(endTime, newEndTime);
        PricingStrategy pricingStrategy = PricingStrategyFactory.getPricingStrategy(clientType);
        double additionalCost = additionalHours * pricingStrategy.getRate();

        // Update the end time and total cost
        this.endTime = newEndTime;
        this.totalCost += additionalCost;

        System.out.println("Parking time extended to: " + newEndTime);
        System.out.println("New total cost: " + this.totalCost);
    }

    public void cancelBooking() {
        // Logic to handle payment refunds
        long minutesSinceStart = ChronoUnit.MINUTES.between(startTime, LocalDateTime.now());
        if (minutesSinceStart < 60) {
            System.out.println("No refund for the deposit as the client is a no-show within the first hour.");
        } else {
            double refundAmount = calculateRefund();
            if (refundAmount > 0) {
                processRefund(refundAmount);
                System.out.println("Refund of " + refundAmount + " processed for booking: " + bookingId);
            } else {
                System.out.println("No refund applicable for booking: " + bookingId);
            }
        }

        System.out.println("Booking canceled: " + bookingId);
    }

    // Method to process payment
    public void processPayment(double amount) {
        if (paymentMethod != null) {
            paymentMethod.processPayment(amount);
            System.out.println("Payment of " + amount + " processed for booking: " + bookingId);
        } else {
            System.out.println("No payment method set for this booking.");
        }
    }

    // Method to process Refund
    public void processRefund(double amount) {
        if (paymentMethod != null) {
            paymentMethod.processRefund(amount);
            System.out.println("Refund of " + amount + " processed for booking: " + bookingId);
        } else {
            System.out.println("No payment method set for this booking. Cannot process refund.");
        }
    }
}