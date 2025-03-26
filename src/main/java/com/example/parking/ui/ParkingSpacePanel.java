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
        
        // Initialize with some example spaces
        addParkingSpace(new ParkingSpace("A1", 10.0));
        addParkingSpace(new ParkingSpace("A2", 10.0));
        addParkingSpace(new ParkingSpace("A3", 10.0));
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
                    JOptionPane.showMessageDialog(this, "Space ID cannot be empty!");
                    return;
                }
                
                if (parkingSpaces.containsKey(id)) {
                    JOptionPane.showMessageDialog(this, "Space ID already exists!");
                    return;
                }
                
                addParkingSpace(new ParkingSpace(id, rate));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid rate value!");
            }
        }
    }
    
    private void addParkingSpace(ParkingSpace space) {
        parkingSpaces.put(space.getId(), space);
        space.attach(this);  // Register as observer
        
        JPanel spacePanel = createSpacePanel(space);
        spaceDisplays.put(space.getId(), spacePanel);
        spacesGrid.add(spacePanel);
        revalidate();
        repaint();
    }
    
    private JPanel createSpacePanel(ParkingSpace space) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Space ID and Status
        JLabel idLabel = new JLabel("Space " + space.getId());
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel statusLabel = new JLabel("Status: " + space.getStatus());
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton bookButton = new JButton("Book");
        JButton occupyButton = new JButton("Occupy");
        JButton vacateButton = new JButton("Vacate");
        JButton toggleButton = new JButton(space.isEnabled() ? "Disable" : "Enable");
        
        // Button actions
        bookButton.addActionListener(e -> {
            space.request();
            updateSpaceDisplay(space);
        });
        
        occupyButton.addActionListener(e -> {
            String plate = JOptionPane.showInputDialog(this, "Enter license plate:");
            if (plate != null && !plate.trim().isEmpty()) {
                space.occupy(plate);
                updateSpaceDisplay(space);
            }
        });
        
        vacateButton.addActionListener(e -> {
            space.vacate();
            updateSpaceDisplay(space);
        });
        
        toggleButton.addActionListener(e -> {
            if (space.isEnabled()) {
                space.disable();
            } else {
                space.enable();
            }
            toggleButton.setText(space.isEnabled() ? "Disable" : "Enable");
            updateSpaceDisplay(space);
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
    
    private void updateSpaceDisplay(ParkingSpace space) {
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