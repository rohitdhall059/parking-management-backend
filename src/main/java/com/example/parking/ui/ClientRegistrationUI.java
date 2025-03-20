package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.parking.service.ClientService;
import com.example.parking.dao.CSVClientDAO;

public class ClientRegistrationUI extends JFrame {

    // UI Components
    private JTextField idField, nameField, emailField, passwordField;
    private JButton registerButton;
    
    // Reference to backend service
    private ClientService clientService;

    public ClientRegistrationUI(ClientService clientService) {
        this.clientService = clientService;
        initialize();
    }

    private void initialize() {
        setTitle("Client Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Use a layout manager for a cleaner design. Here, we use GridBagLayout.
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.anchor = GridBagConstraints.WEST;

        // Client ID Label & Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Client ID:"), gbc);
        
        gbc.gridx = 1;
        idField = new JTextField(20);
        panel.add(idField, gbc);

        // Name Label & Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Email Label & Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Password Label & Field (optional)
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JTextField(20);
        panel.add(passwordField, gbc);

        // Register Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        // Button action: When clicked, call backend registration
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerClient();
            }
        });

        add(panel);
    }

    private void registerClient() {
        // Get values from text fields
        String clientId = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            // Call the backend service to register the client.
            // Adjust if your ClientService.registerClient method requires a password.
            clientService.registerClient(clientId, name, email);
            JOptionPane.showMessageDialog(this, "Client registered successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        // Set up the backend: Create DAO and service objects.
        // Ensure that your CSV file paths are correct.
        CSVClientDAO clientDAO = new CSVClientDAO("data/clients.csv");
        ClientService clientService = new ClientService(clientDAO);

        // Launch the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientRegistrationUI(clientService).setVisible(true);
            }
        });
    }
}