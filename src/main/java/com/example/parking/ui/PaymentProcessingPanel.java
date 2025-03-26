package com.example.parking.ui;

import javax.swing.*;
import java.awt.*;
import com.example.parking.model.*;

public class PaymentProcessingPanel extends JPanel {
    private JComboBox<String> paymentMethodCombo;
    private JTextField bookingIdField;
    private JTextField amountField;
    private JTextField cardNumberField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private JButton processButton;
    private JTextArea transactionHistoryArea;

    public PaymentProcessingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        paymentMethodCombo = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "Cash"});
        bookingIdField = new JTextField(20);
        amountField = new JTextField(10);
        cardNumberField = new JTextField(16);
        expiryDateField = new JTextField(5);
        cvvField = new JTextField(3);
        processButton = new JButton("Process Payment");
        transactionHistoryArea = new JTextArea(10, 40);
        transactionHistoryArea.setEditable(false);

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Booking ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(bookingIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        formPanel.add(paymentMethodCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Card Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cardNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Expiry Date (MM/YY):"), gbc);
        gbc.gridx = 1;
        formPanel.add(expiryDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cvvField, gbc);

        // Add action listener
        processButton.addActionListener(e -> processPayment());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(processButton);

        // Create transaction history panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        historyPanel.add(new JScrollPane(transactionHistoryArea), BorderLayout.CENTER);

        // Add panels to main panel
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.SOUTH);

        // Add payment method change listener
        paymentMethodCombo.addActionListener(e -> updatePaymentFields());
        updatePaymentFields(); // Initial update
    }

    private void updatePaymentFields() {
        boolean isCard = paymentMethodCombo.getSelectedItem().toString().contains("Card");
        cardNumberField.setEnabled(isCard);
        expiryDateField.setEnabled(isCard);
        cvvField.setEnabled(isCard);
    }

    private void processPayment() {
        try {
            String bookingId = bookingIdField.getText();
            String paymentMethod = paymentMethodCombo.getSelectedItem().toString();
            double amount = Double.parseDouble(amountField.getText());

            if (bookingId.isEmpty()) {
                throw new IllegalArgumentException("Booking ID is required");
            }

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            if (paymentMethod.contains("Card")) {
                validateCardDetails();
            }

            // Process payment (simplified for example)
            String timestamp = java.time.LocalDateTime.now().toString();
            String transactionRecord = String.format(
                "[%s] Payment processed for Booking %s\n" +
                "Method: %s\n" +
                "Amount: $%.2f\n" +
                "Status: Success\n\n",
                timestamp, bookingId, paymentMethod, amount
            );

            transactionHistoryArea.append(transactionRecord);
            clearFields();
            JOptionPane.showMessageDialog(this, "Payment processed successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error processing payment: " + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateCardDetails() throws IllegalArgumentException {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number");
        }

        if (expiryDate.isEmpty() || !expiryDate.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Invalid expiry date format (MM/YY)");
        }

        if (cvv.isEmpty() || !cvv.matches("\\d{3}")) {
            throw new IllegalArgumentException("Invalid CVV");
        }
    }

    private void clearFields() {
        bookingIdField.setText("");
        amountField.setText("");
        cardNumberField.setText("");
        expiryDateField.setText("");
        cvvField.setText("");
        paymentMethodCombo.setSelectedIndex(0);
    }
} 