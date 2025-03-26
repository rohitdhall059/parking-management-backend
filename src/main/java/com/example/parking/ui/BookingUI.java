package com.example.parking.ui;

import com.example.parking.model.Booking;
import com.example.parking.model.payment.PaymentMethod;
import com.example.parking.service.BookingService;
import com.example.parking.dao.BookingDAO;
import com.example.parking.dao.ClientDAO;
import com.example.parking.dao.ParkingSpaceDAO;
import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.factory.PaymentMethodFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A complete Swing UI for creating bookings, including a main method
 * that instantiates the backend objects (DAOs and service).
 */
public class BookingUI extends JFrame {

    private final BookingService bookingService;
    private final JTextField clientIdField;
    private final JTextField spaceIdField;
    private final JTextField startTimeField;
    private final JTextField endTimeField;
    private final JComboBox<String> paymentTypeCombo;
    private final JTextField cardNumberField;
    private final JTextField credentialField;
    private final JTextArea resultArea;

    public BookingUI() {
        // Initialize DAOs
        ClientDAO clientDAO = new CSVClientDAO();
        ParkingSpaceDAO parkingSpaceDAO = new CSVParkingSpaceDAO();
        BookingDAO bookingDAO = new CSVBookingDAO(clientDAO, parkingSpaceDAO);

        // Initialize BookingService
        bookingService = new BookingService(bookingDAO, clientDAO, parkingSpaceDAO);

        // Create UI components
        setTitle("Parking Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Client ID:"));
        clientIdField = new JTextField();
        inputPanel.add(clientIdField);

        inputPanel.add(new JLabel("Space ID:"));
        spaceIdField = new JTextField();
        inputPanel.add(spaceIdField);

        inputPanel.add(new JLabel("Start Time (yyyy-MM-dd HH:mm):"));
        startTimeField = new JTextField();
        inputPanel.add(startTimeField);

        inputPanel.add(new JLabel("End Time (yyyy-MM-dd HH:mm):"));
        endTimeField = new JTextField();
        inputPanel.add(endTimeField);

        inputPanel.add(new JLabel("Payment Type:"));
        paymentTypeCombo = new JComboBox<>(new String[]{"credit", "debit", "mobile"});
        inputPanel.add(paymentTypeCombo);

        inputPanel.add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        inputPanel.add(cardNumberField);

        inputPanel.add(new JLabel("Credential:"));
        credentialField = new JTextField();
        inputPanel.add(credentialField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book Space");
        JButton viewButton = new JButton("View Bookings");
        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);

        // Result area
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add action listeners
        bookButton.addActionListener(e -> createBooking());
        viewButton.addActionListener(e -> viewBookings());

        pack();
        setLocationRelativeTo(null);
    }

    private void createBooking() {
        try {
            String clientId = clientIdField.getText();
            String spaceId = spaceIdField.getText();
            String startTimeStr = startTimeField.getText();
            String endTimeStr = endTimeField.getText();
            String paymentType = (String) paymentTypeCombo.getSelectedItem();
            String cardNumber = cardNumberField.getText();
            String credential = credentialField.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

            PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(
                    paymentType, cardNumber, credential);

            Booking booking = bookingService.createBooking(clientId, spaceId, startTime, endTime, paymentMethod);
            resultArea.setText("Booking created successfully!\nBooking ID: " + booking.getId());
        } catch (Exception e) {
            resultArea.setText("Error creating booking: " + e.getMessage());
        }
    }

    private void viewBookings() {
        try {
            String clientId = clientIdField.getText();
            List<Booking> bookings = bookingService.getBookingsByClient(clientId);
            
            StringBuilder sb = new StringBuilder("Bookings for client " + clientId + ":\n\n");
            for (Booking booking : bookings) {
                sb.append("Booking ID: ").append(booking.getId()).append("\n");
                sb.append("Space ID: ").append(booking.getParkingSpace().getId()).append("\n");
                sb.append("Start Time: ").append(booking.getStartTime()).append("\n");
                sb.append("End Time: ").append(booking.getEndTime()).append("\n");
                sb.append("Status: ").append(booking.getStatus()).append("\n");
                sb.append("Amount: ").append(booking.getAmount()).append("\n\n");
            }
            resultArea.setText(sb.toString());
        } catch (Exception e) {
            resultArea.setText("Error viewing bookings: " + e.getMessage());
        }
    }

    /**
     * Main method to launch the Booking UI.
     * This is where you instantiate your DAOs and service objects,
     * then pass a valid BookingService into the UI constructor.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookingUI().setVisible(true);
        });
    }
}