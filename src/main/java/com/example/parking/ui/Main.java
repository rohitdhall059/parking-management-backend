package com.example.parking;

import com.example.parking.dao.CSVClientDAO;
import com.example.parking.dao.CSVParkingSpaceDAO;
import com.example.parking.dao.CSVBookingDAO;
import com.example.parking.model.Client;
import com.example.parking.model.ParkingSpace;
import com.example.parking.model.Student;
import com.example.parking.model.FacultyMember;
import com.example.parking.model.NonFacultyStaff;
import com.example.parking.model.Visitor;
import com.example.parking.model.Car; // Import the Car class
import com.example.parking.service.ClientService;
import com.example.parking.service.ParkingSpaceService;
import com.example.parking.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1) Instantiate DAOs
        CSVClientDAO clientDao = new CSVClientDAO("data/clients.csv");
        CSVParkingSpaceDAO spaceDao = new CSVParkingSpaceDAO("data/parkingspaces.csv");
        CSVBookingDAO bookingDao = new CSVBookingDAO("data/bookings.csv");

        // 2) Instantiate Services
        ClientService clientService = new ClientService(clientDao);
        ParkingSpaceService spaceService = new ParkingSpaceService(spaceDao);
        BookingService bookingService = new BookingService(bookingDao, clientDao, spaceDao);

        // 3) Demo client registration
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client ID:");
        String clientId = scanner.nextLine();

        System.out.println("Enter client name:");
        String clientName = scanner.nextLine();

        System.out.println("Enter client email:");
        String clientEmail = scanner.nextLine();

        // Prompt for car information
        System.out.println("Enter car model:");
        String carModel = scanner.nextLine();

        System.out.println("Enter car license plate:");
        String carLicensePlate = scanner.nextLine();

        // Create a Car object
        Car car = new Car(carModel, carLicensePlate);

        System.out.println("Select client category (1: Visitor, 2: Faculty, 3: Non-Faculty, 4: Student):");
        int categoryChoice = scanner.nextInt();
        Client client;

        switch (categoryChoice) {
            case 1:
                client = new Visitor(clientId, clientName, clientEmail, car);
                break;
            case 2:
                client = new FacultyMember(clientId, clientName, clientEmail, car);
                break;
            case 3:
                client = new NonFacultyStaff(clientId, clientName, clientEmail, car);
                break;
            case 4:
                client = new Student(clientId, clientName, clientEmail, car);
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Visitor.");
                client = new Visitor(clientId, clientName, clientEmail, car);
        }

        // Register the client
        clientService.registerClient(client);

        // 4) Demo adding a new parking space
        spaceDao.save(new ParkingSpace("S900", 3.0));  // newly added space

        // 5) List all available spaces
        List<ParkingSpace> available = spaceService.getAvailableSpaces();
        System.out.println("Available spaces: " + available);

        // 6) Create a booking
        bookingService.createBooking("B900", clientId, "S900", LocalDateTime.of(2025, 3, 20, 10, 0), LocalDateTime.of(2025, 3, 20, 12, 0));

        // 7) See that space is now occupied
        System.out.println("Available after booking: " + spaceService.getAvailableSpaces());
    }
}