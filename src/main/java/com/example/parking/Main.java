package com.example.parking;

import com.example.parking.dao.BookingDAO;
import com.example.parking.dao.ClientDAO;
import com.example.parking.dao.ParkingSpaceDAO;
import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.service.BookingService;
import com.example.parking.service.ClientService;
import com.example.parking.service.ParkingSpaceService;
import com.example.parking.service.PaymentService;
import com.example.parking.ui.BookingUI;

public class Main {
    public static void main(String[] args) {
        // Initialize DAOs
        ClientDAO clientDAO = new CSVClientDAO();
        ParkingSpaceDAO parkingSpaceDAO = new CSVParkingSpaceDAO();
        BookingDAO bookingDAO = new CSVBookingDAO(clientDAO, parkingSpaceDAO);

        // Initialize services
        BookingService bookingService = new BookingService(bookingDAO, clientDAO, parkingSpaceDAO);
        ClientService clientService = new ClientService(clientDAO);
        ParkingSpaceService parkingSpaceService = new ParkingSpaceService(parkingSpaceDAO);
        PaymentService paymentService = new PaymentService(bookingDAO);

        // Create and show the UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            BookingUI ui = new BookingUI();
            ui.setVisible(true);
        });
    }
} 