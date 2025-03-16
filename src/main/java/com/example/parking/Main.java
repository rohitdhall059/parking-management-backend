package com.example.parking;

import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.model.Client;
import com.example.parking.model.ParkingSpace;
import com.example.parking.service.ClientService;
import com.example.parking.service.ParkingSpaceService;
import com.example.parking.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1) Instantiate DAOs
        CSVClientDAO clientDao = new CSVClientDAO("data/clients.csv");
        CSVParkingSpaceDAO spaceDao = new CSVParkingSpaceDAO("data/parkingspaces.csv");
        CSVBookingDAO bookingDao = new CSVBookingDAO("data/bookings.csv");

        // 2) Instantiate Services
        ClientService clientService = new ClientService(clientDao);
        ParkingSpaceService spaceService = new ParkingSpaceService(spaceDao);
        BookingService bookingService = new BookingService(bookingDao, clientDao, spaceDao);

        // 3) Demo client registration
        clientService.registerClient("C100", "Alice Wonderland", "alice@example.com");

        // 4) Demo adding a new parking space
        spaceDao.save(new ParkingSpace("S900", 3.0));  // newly added space

        // 5) List all available spaces
        List<ParkingSpace> available = spaceService.getAvailableSpaces();
        System.out.println("Available spaces: " + available);

        // 6) Create a booking
        bookingService.createBooking(
            "B900",
            "C100",
            "S900",
            LocalDateTime.of(2025, 3, 20, 10, 0),
            LocalDateTime.of(2025, 3, 20, 12, 0)
        );

        // 7) See that space is now occupied
        System.out.println("Available after booking: " + spaceService.getAvailableSpaces());
    }
}
