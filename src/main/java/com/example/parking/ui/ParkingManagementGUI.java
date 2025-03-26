package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import com.example.parking.model.*;
import com.example.parking.state.*;
import com.example.parking.observer.*;

public class ParkingManagementGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private ClientRegistrationPanel registrationPanel;
    private BookingManagementPanel bookingPanel;
    private PaymentProcessingPanel paymentPanel;

    public ParkingManagementGUI() {
        setTitle("Parking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize components
        tabbedPane = new JTabbedPane();
        registrationPanel = new ClientRegistrationPanel();
        bookingPanel = new BookingManagementPanel();
        paymentPanel = new PaymentProcessingPanel();

        // Add tabs
        tabbedPane.addTab("Client Registration", registrationPanel);
        tabbedPane.addTab("Booking Management", bookingPanel);
        tabbedPane.addTab("Payment Processing", paymentPanel);
        tabbedPane.addTab("Parking Spaces", new ParkingSpacePanel());

        // Add to frame
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ParkingManagementGUI gui = new ParkingManagementGUI();
            gui.setVisible(true);
        });
    }
} 