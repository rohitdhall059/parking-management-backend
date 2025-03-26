# Parking Management System

A Java Swing-based application for managing parking lots, including client registration, booking management, and payment processing.

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/yourusername/parking-management-backend.git
cd parking-management-backend
```

2. Compile the project:
```bash
mvn clean compile
```

3. Run the application:
```bash
mvn exec:java -Dexec.mainClass="com.example.parking.ui.ParkingManagementGUI"
```

Alternatively, you can run the compiled JAR file:
```bash
mvn package
java -jar target/parking-management-1.0-SNAPSHOT.jar
```

## Features

- Client Registration
  - Register different types of clients (Student, Faculty Member, Non-Faculty Staff, Visitor)
  - Input validation for all fields
  - Clear form functionality

- Booking Management
  - Create new parking space bookings
  - Edit existing bookings
  - Cancel bookings
  - View list of current bookings

- Payment Processing
  - Multiple payment methods (Credit Card, Debit Card, Cash)
  - Card detail validation
  - Transaction history
  - Dynamic form fields based on payment method

## Usage

1. Client Registration:
   - Fill in all required fields
   - Select client type
   - Click "Register" button

2. Booking Management:
   - Enter client ID and booking details
   - Select parking space
   - Enter booking duration
   - Use buttons to create, edit, or cancel bookings

3. Payment Processing:
   - Enter booking ID
   - Select payment method
   - Enter payment details
   - Click "Process Payment" button 