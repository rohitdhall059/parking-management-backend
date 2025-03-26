package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.parking.service.ClientService;
import com.example.parking.service.BookingService;
import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;

public class Main {
    private JFrame mainFrame;
    private ClientService clientService;
    private BookingService bookingService;

    public Main() {
        // Initialize DAOs
        CSVClientDAO clientDAO = new CSVClientDAO("data/clients.csv");
        CSVBookingDAO bookingDAO = new CSVBookingDAO("data/bookings.csv");
        CSVParkingSpaceDAO spaceDAO = new CSVParkingSpaceDAO("data/parkingspaces.csv");

        // Initialize Services
        this.clientService = new ClientService(clientDAO);
        this.bookingService = new BookingService(bookingDAO, clientDAO, spaceDAO);

        // Initialize UI
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Parking Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLocationRelativeTo(null);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title label
        JLabel titleLabel = new JLabel("Welcome to Parking Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create buttons
        JButton registerButton = new JButton("Register New Client");
        JButton bookButton = new JButton("Book Parking Space");
        JButton viewButton = new JButton("View Parking Status");

        // Add button actions
        registerButton.addActionListener(e -> showRegistrationDialog());
        bookButton.addActionListener(e -> showBookingDialog());
        viewButton.addActionListener(e -> showParkingStatusDialog());

        // Add buttons to panel
        buttonPanel.add(registerButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to frame
        mainFrame.add(mainPanel);
    }

    private void showRegistrationDialog() {
        ClientRegistrationUI registrationUI = new ClientRegistrationUI(clientService);
        registrationUI.setVisible(true);
    }

    private void showBookingDialog() {
        BookingUI bookingUI = new BookingUI(bookingService);
        bookingUI.setVisible(true);
    }

    private void showParkingStatusDialog() {
        // Create a custom dialog
        JDialog dialog = new JDialog(mainFrame, "Parking Status", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a table model for parking spaces
        String[] columnNames = {"Space ID", "Status", "Occupied By"};
        Object[][] data = {
            {"A1", "Available", ""},
            {"A2", "Occupied", "Client123"},
            {"B1", "Available", ""},
            {"B2", "Maintenance", ""},
            {"C1", "Available", ""},
            {"C2", "Occupied", "Client456"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.show();
        });
    }
}