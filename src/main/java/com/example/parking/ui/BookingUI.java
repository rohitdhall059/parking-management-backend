package com.example.parking.ui;

import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A complete Swing UI for creating bookings, including a main method
 * that instantiates the backend objects (DAOs and service).
 */
public class BookingUI extends JFrame {

    private JTextField bookingIdField, clientIdField, spaceIdField, startTimeField, endTimeField;
    private JButton createBookingButton;

    // The BookingService is injected via the constructor
    private BookingService bookingService;

    public BookingUI(BookingService bookingService) {
        this.bookingService = bookingService;
        initialize();
    }

    private void initialize() {
        setTitle("Create Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a 6x2 grid layout with 10px gaps
        GridLayout layout = new GridLayout(6, 2, 10, 10);
        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Row 1: Booking ID
        panel.add(new JLabel("Booking ID:"));
        bookingIdField = new JTextField();
        panel.add(bookingIdField);

        // Row 2: Client ID
        panel.add(new JLabel("Client ID:"));
        clientIdField = new JTextField();
        panel.add(clientIdField);

        // Row 3: Space ID
        panel.add(new JLabel("Space ID:"));
        spaceIdField = new JTextField();
        panel.add(spaceIdField);

        // Row 4: Start Time (yyyy-MM-dd HH:mm)
        panel.add(new JLabel("Start Time (yyyy-MM-dd HH:mm):"));
        startTimeField = new JTextField();
        panel.add(startTimeField);

        // Row 5: End Time (yyyy-MM-dd HH:mm)
        panel.add(new JLabel("End Time (yyyy-MM-dd HH:mm):"));
        endTimeField = new JTextField();
        panel.add(endTimeField);

        // Row 6: Create Booking Button
        createBookingButton = new JButton("Create Booking");
        // Add a filler label to align the button in the grid
        panel.add(new JLabel(""));
        panel.add(createBookingButton);

        createBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBooking();
            }
        });

        add(panel);
        pack(); // Size the frame based on the layout
        setLocationRelativeTo(null); // Center on screen
    }

    private void createBooking() {
        try {
            // Get user input
            String bookingId = bookingIdField.getText().trim();
            String clientId = clientIdField.getText().trim();
            String spaceId = spaceIdField.getText().trim();
            String startStr = startTimeField.getText().trim(); // e.g., "2025-03-20 10:30"
            String endStr = endTimeField.getText().trim();     // e.g., "2025-03-20 12:00"

            // Parse date/time with a space using a custom formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endStr, formatter);

            // Call the backend service to create the booking
            bookingService.createBooking(bookingId, clientId, spaceId, startTime, endTime);

            // Show success
            JOptionPane.showMessageDialog(this, "Booking created successfully!");
        } catch (Exception ex) {
            // Show error if parsing or creation fails
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Booking Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to launch the Booking UI.
     * This is where you instantiate your DAOs and service objects,
     * then pass a valid BookingService into the UI constructor.
     */
    public static void main(String[] args) {
        // 1. Create DAO objects (adjust file paths to match your project)
        CSVBookingDAO bookingDAO = new CSVBookingDAO("data/bookings.csv");
        CSVClientDAO clientDAO = new CSVClientDAO("data/clients.csv");
        CSVParkingSpaceDAO spaceDAO = new CSVParkingSpaceDAO("data/parkingspaces.csv");

        // 2. Create the BookingService
        BookingService bookingService = new BookingService(bookingDAO, clientDAO, spaceDAO);

        // 3. Launch the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new BookingUI(bookingService).setVisible(true);
        });
    }
}