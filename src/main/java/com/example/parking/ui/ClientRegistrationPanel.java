package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import com.example.parking.model.*;
import com.example.parking.strategy.*;

public class ClientRegistrationPanel extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField idField;
    private JTextField departmentField;
    private JComboBox<String> clientTypeCombo;
    private JButton registerButton;

    public ClientRegistrationPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        nameField = new JTextField(20);
        idField = new JTextField(20);
        departmentField = new JTextField(20);
        clientTypeCombo = new JComboBox<>(new String[]{"Student", "Faculty Member", "Non-Faculty Staff", "Visitor"});
        registerButton = new JButton("Register");

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Client Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(clientTypeCombo, gbc);

        // Add register button
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(registerButton, gbc);

        // Add action listener
        registerButton.addActionListener(e -> registerClient());

        // Add form to panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
    }

    private void registerClient() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText();
        String id = idField.getText();
        String department = departmentField.getText();
        String clientType = (String) clientTypeCombo.getSelectedItem();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create appropriate client based on type
        Client client = null;
        ParkingRateStrategy rateStrategy = null;

        try {
            switch (clientType) {
                case "Student":
                    rateStrategy = new StudentRateStrategy();
                    client = new Student(email, password, name, rateStrategy, id, department);
                    break;
                case "Faculty Member":
                    rateStrategy = new FacultyRateStrategy();
                    client = new FacultyMember(email, password, name, rateStrategy, id, department);
                    break;
                case "Non-Faculty Staff":
                    rateStrategy = new StaffRateStrategy();
                    client = new NonFacultyStaff(email, password, name, rateStrategy, id, department);
                    break;
                case "Visitor":
                    rateStrategy = new VisitorRateStrategy();
                    client = new Visitor(email, password, name, rateStrategy, id, new java.util.Date(), "General Visit");
                    break;
            }

            // TODO: Save client to database or data store
            JOptionPane.showMessageDialog(this, "Client registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error registering client: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        nameField.setText("");
        idField.setText("");
        departmentField.setText("");
        clientTypeCombo.setSelectedIndex(0);
    }
} 