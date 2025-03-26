package com.example.parking.ui;

import com.example.parking.model.ParkingSpace;
import com.example.parking.observer.Observer;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ParkingSpacePanel extends JPanel implements Observer {
    private Map<String, ParkingSpace> parkingSpaces;
    private Map<String, JPanel> spaceDisplays;
    private JPanel spacesGrid;
    
    public ParkingSpacePanel() {
        this.parkingSpaces = new HashMap<>();
        this.spaceDisplays = new HashMap<>();
        
        setLayout(new BorderLayout());
        
        // Create header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(new JLabel("Parking Space Management"));
        JButton addSpaceButton = new JButton("Add New Space");
        addSpaceButton.addActionListener(e -> showAddSpaceDialog());
        headerPanel.add(addSpaceButton);
        add(headerPanel, BorderLayout.NORTH);
        
        // Create grid for parking spaces
        spacesGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        spacesGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(spacesGrid);
        add(scrollPane, BorderLayout.CENTER);
        
        try {
            // Initialize with some example spaces
            addParkingSpace(new ParkingSpace("A1", 10.0));
            addParkingSpace(new ParkingSpace("A2", 10.0));
            addParkingSpace(new ParkingSpace("A3", 10.0));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing parking spaces: " + e.getMessage());
        }
    }
    
    private void showAddSpaceDialog() {
        JTextField idField = new JTextField(10);
        JTextField rateField = new JTextField(10);
        
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Space ID:"));
        panel.add(idField);
        panel.add(new JLabel("Rate per hour:"));
        panel.add(rateField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Add New Parking Space", JOptionPane.OK_CANCEL_OPTION);
                
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText().trim();
                double rate = Double.parseDouble(rateField.getText().trim());
                
                if (id.isEmpty()) {
                    throw new IllegalArgumentException("Space ID cannot be empty!");
                }
                
                if (parkingSpaces.containsKey(id)) {
                    throw new IllegalArgumentException("Space ID already exists!");
                }
                
                addParkingSpace(new ParkingSpace(id, rate));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid rate value! Please enter a valid number.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error creating parking space: " + e.getMessage(), 
                    "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addParkingSpace(ParkingSpace space) {
        try {
            parkingSpaces.put(space.getId(), space);
            space.attach(this);  // Register as observer
            
            JPanel spacePanel = createSpacePanel(space);
            spaceDisplays.put(space.getId(), spacePanel);
            spacesGrid.add(spacePanel);
            revalidate();
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding parking space: " + e.getMessage(), 
                "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createSpacePanel(ParkingSpace space) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Space ID and Status
        JLabel idLabel = new JLabel("Space " + space.getId());
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel statusLabel = new JLabel(getStatusWithColor(space));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton bookButton = new JButton("Book");
        JButton occupyButton = new JButton("Occupy");
        JButton vacateButton = new JButton("Vacate");
        JButton toggleButton = new JButton(space.isEnabled() ? "Disable" : "Enable");
        
        // Set button states based on space status
        bookButton.setEnabled(space.isEnabled() && !space.isOccupied());
        occupyButton.setEnabled(space.isEnabled() && !space.isOccupied());
        vacateButton.setEnabled(space.isEnabled() && space.isOccupied());
        
        // Button actions with error handling
        bookButton.addActionListener(e -> {
            try {
                space.request();
                updateSpaceDisplay(space);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error booking space: " + ex.getMessage(), 
                    "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        occupyButton.addActionListener(e -> {
            try {
                String plate = JOptionPane.showInputDialog(this, "Enter license plate:");
                if (plate != null && !plate.trim().isEmpty()) {
                    space.occupy(plate);
                    updateSpaceDisplay(space);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error occupying space: " + ex.getMessage(), 
                    "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        vacateButton.addActionListener(e -> {
            try {
                space.vacate();
                updateSpaceDisplay(space);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error vacating space: " + ex.getMessage(), 
                    "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        toggleButton.addActionListener(e -> {
            try {
                if (space.isEnabled()) {
                    space.disable();
                } else {
                    space.enable();
                }
                toggleButton.setText(space.isEnabled() ? "Disable" : "Enable");
                updateSpaceDisplay(space);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error toggling space state: " + ex.getMessage(), 
                    "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(bookButton);
        buttonPanel.add(occupyButton);
        buttonPanel.add(vacateButton);
        buttonPanel.add(toggleButton);
        
        // Car info (if occupied)
        JLabel carInfoLabel = new JLabel(space.getCarInfo() != null ? space.getCarInfo() : "");
        carInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to panel
        panel.add(Box.createVerticalStrut(10));
        panel.add(idLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(statusLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(carInfoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));
        
        return panel;
    }
    
    private String getStatusWithColor(ParkingSpace space) {
        String status = space.getStatus();
        String color = switch (status) {
            case "Available" -> "green";
            case "Occupied" -> "red";
            case "Booked" -> "orange";
            case "Disabled" -> "gray";
            default -> "black";
        };
        return String.format("<html>Status: <font color='%s'>%s</font></html>", color, status);
    }
    
    private void updateSpaceDisplay(ParkingSpace space) {
        try {
            JPanel oldPanel = spaceDisplays.get(space.getId());
            int index = getComponentIndex(oldPanel);
            if (index != -1) {
                spacesGrid.remove(index);
                JPanel newPanel = createSpacePanel(space);
                spaceDisplays.put(space.getId(), newPanel);
                spacesGrid.add(newPanel, index);
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating display: " + e.getMessage(), 
                "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int getComponentIndex(Component component) {
        Component[] components = spacesGrid.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == component) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void update(ParkingSpace space) {
        // Update the display when the space state changes
        SwingUtilities.invokeLater(() -> updateSpaceDisplay(space));
    }
} 