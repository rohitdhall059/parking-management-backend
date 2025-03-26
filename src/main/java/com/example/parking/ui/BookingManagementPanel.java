package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.parking.model.*;

public class BookingManagementPanel extends JPanel {
    private JTextField clientIdField;
    private JComboBox<String> parkingSpaceCombo;
    private JTextField licensePlateField;
    private JTextField startDateField;
    private JTextField startTimeField;
    private JTextField endDateField;
    private JTextField endTimeField;
    private JButton createButton;
    private JButton editButton;
    private JButton cancelButton;
    private JList<Booking> bookingsList;
    private DefaultListModel<Booking> bookingsModel;

    public BookingManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        clientIdField = new JTextField(20);
        parkingSpaceCombo = new JComboBox<>(new String[]{"A1", "A2", "B1", "B2", "C1", "C2"}); // Example spaces
        licensePlateField = new JTextField(20);
        startDateField = new JTextField(10);
        startTimeField = new JTextField(5);
        endDateField = new JTextField(10);
        endTimeField = new JTextField(5);
        createButton = new JButton("Create Booking");
        editButton = new JButton("Edit Booking");
        cancelButton = new JButton("Cancel Booking");
        bookingsModel = new DefaultListModel<>();
        bookingsList = new JList<>(bookingsModel);

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Client ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(clientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Parking Space:"), gbc);
        gbc.gridx = 1;
        formPanel.add(parkingSpaceCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("License Plate:"), gbc);
        gbc.gridx = 1;
        formPanel.add(licensePlateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(startDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Start Time (HH:mm):"), gbc);
        gbc.gridx = 1;
        formPanel.add(startTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(endDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("End Time (HH:mm):"), gbc);
        gbc.gridx = 1;
        formPanel.add(endTimeField, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(cancelButton);

        // Add action listeners
        createButton.addActionListener(e -> createBooking());
        editButton.addActionListener(e -> editBooking());
        cancelButton.addActionListener(e -> cancelBooking());

        // Create bookings list panel
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Current Bookings"));
        listPanel.add(new JScrollPane(bookingsList), BorderLayout.CENTER);

        // Add panels to main panel
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);
    }

    private void createBooking() {
        try {
            String clientId = clientIdField.getText();
            String spaceId = (String) parkingSpaceCombo.getSelectedItem();
            String licensePlate = licensePlateField.getText();
            String startDateTime = startDateField.getText() + " " + startTimeField.getText();
            String endDateTime = endDateField.getText() + " " + endTimeField.getText();

            // Create a new booking
            ParkingSpace space = new ParkingSpace(spaceId, 0); // Simplified for example
            Booking booking = new Booking(space, licensePlate, startDateTime, endDateTime);
            
            // Add to list
            bookingsModel.addElement(booking);
            
            // Clear fields
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Booking created successfully!");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating booking: " + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBooking() {
        Booking selectedBooking = bookingsList.getSelectedValue();
        if (selectedBooking == null) {
            JOptionPane.showMessageDialog(this, "Please select a booking to edit!");
            return;
        }

        try {
            String newEndDateTime = endDateField.getText() + " " + endTimeField.getText();
            selectedBooking.extend(newEndDateTime);
            bookingsList.repaint();
            JOptionPane.showMessageDialog(this, "Booking updated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating booking: " + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBooking() {
        Booking selectedBooking = bookingsList.getSelectedValue();
        if (selectedBooking == null) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this booking?", 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            selectedBooking.cancel();
            bookingsModel.removeElement(selectedBooking);
            JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
        }
    }

    private void clearFields() {
        clientIdField.setText("");
        licensePlateField.setText("");
        startDateField.setText("");
        startTimeField.setText("");
        endDateField.setText("");
        endTimeField.setText("");
        parkingSpaceCombo.setSelectedIndex(0);
    }
} 