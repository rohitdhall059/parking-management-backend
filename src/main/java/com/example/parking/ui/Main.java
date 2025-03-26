package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create the main window
            JFrame mainFrame = new JFrame("Parking Management System");
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
            registerButton.addActionListener(e -> showRegistrationDialog(mainFrame));
            bookButton.addActionListener(e -> showBookingDialog(mainFrame));
            viewButton.addActionListener(e -> showParkingStatusDialog(mainFrame));

            // Add buttons to panel
            buttonPanel.add(registerButton);
            buttonPanel.add(bookButton);
            buttonPanel.add(viewButton);

            // Add button panel to main panel
            mainPanel.add(buttonPanel, BorderLayout.CENTER);

            // Add main panel to frame
            mainFrame.add(mainPanel);

            // Make the frame visible
            mainFrame.setVisible(true);
        });
    }

    private static void showRegistrationDialog(JFrame parentFrame) {
        // Create a custom dialog
        JDialog dialog = new JDialog(parentFrame, "Register New Client", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(parentFrame);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Add form fields
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField carModelField = new JTextField();
        JTextField licensePlateField = new JTextField();
        JComboBox<String> clientTypeCombo = new JComboBox<>(new String[]{"Visitor", "Faculty Member", "Non-Faculty Staff", "Student"});

        // Add labels and fields to form
        formPanel.add(new JLabel("Client ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Car Model:"));
        formPanel.add(carModelField);
        formPanel.add(new JLabel("License Plate:"));
        formPanel.add(licensePlateField);
        formPanel.add(new JLabel("Client Type:"));
        formPanel.add(clientTypeCombo);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Add button actions
        submitButton.addActionListener(e -> {
            // Here we would process the registration
            JOptionPane.showMessageDialog(dialog, 
                "Client registered successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);

        // Show the dialog
        dialog.setVisible(true);
    }

    private static void showBookingDialog(JFrame parentFrame) {
        // Create a custom dialog
        JDialog dialog = new JDialog(parentFrame, "Book Parking Space", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(parentFrame);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Add form fields
        JTextField clientIdField = new JTextField();
        JComboBox<String> spaceCombo = new JComboBox<>(new String[]{"A1", "A2", "B1", "B2", "C1", "C2"}); // Example spaces
        JTextField startDateField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endDateField = new JTextField();
        JTextField endTimeField = new JTextField();
        JComboBox<String> paymentMethodCombo = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "Mobile Payment"});
        JLabel totalCostLabel = new JLabel("Total Cost: $0.00");

        // Add labels and fields to form
        formPanel.add(new JLabel("Client ID:"));
        formPanel.add(clientIdField);
        formPanel.add(new JLabel("Parking Space:"));
        formPanel.add(spaceCombo);
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        formPanel.add(startDateField);
        formPanel.add(new JLabel("Start Time (HH:MM):"));
        formPanel.add(startTimeField);
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        formPanel.add(endDateField);
        formPanel.add(new JLabel("End Time (HH:MM):"));
        formPanel.add(endTimeField);
        formPanel.add(new JLabel("Payment Method:"));
        formPanel.add(paymentMethodCombo);
        formPanel.add(new JLabel(""));
        formPanel.add(totalCostLabel);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateButton = new JButton("Calculate Cost");
        JButton submitButton = new JButton("Submit Booking");
        JButton cancelButton = new JButton("Cancel");

        // Add button actions
        calculateButton.addActionListener(e -> {
            try {
                // Parse dates and times
                String startDateTime = startDateField.getText() + " " + startTimeField.getText();
                String endDateTime = endDateField.getText() + " " + endTimeField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
                LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);

                // Calculate hours (simple example)
                long hours = java.time.Duration.between(start, end).toHours();
                if (hours <= 0) hours = 1; // minimum 1 hour
                
                // Calculate cost (example rate: $5 per hour)
                double totalCost = hours * 5.0;
                totalCostLabel.setText(String.format("Total Cost: $%.2f", totalCost));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Please enter valid date and time format",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        submitButton.addActionListener(e -> {
            if (totalCostLabel.getText().equals("Total Cost: $0.00")) {
                JOptionPane.showMessageDialog(dialog,
                    "Please calculate the cost first",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(dialog,
                "Booking created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(calculateButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);

        // Show the dialog
        dialog.setVisible(true);
    }

    private static void showParkingStatusDialog(JFrame parentFrame) {
        // Create a custom dialog
        JDialog dialog = new JDialog(parentFrame, "Parking Status", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);

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

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        refreshButton.addActionListener(e -> {
            // Here we would refresh the parking status
            JOptionPane.showMessageDialog(dialog,
                "Parking status refreshed",
                "Refresh",
                JOptionPane.INFORMATION_MESSAGE);
        });

        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // Add components to dialog
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);

        // Show the dialog
        dialog.setVisible(true);
    }
}