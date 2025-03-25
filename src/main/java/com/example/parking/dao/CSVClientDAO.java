package com.example.parking.dao;

import com.example.parking.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVClientDAO implements DAO<Client> {

    private final String csvFilePath;

    public CSVClientDAO(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        // Create the directory if it doesn't exist
        File file = new File(csvFilePath);
        file.getParentFile().mkdirs();
    }

    @Override
    public Client getById(String id) {
        List<Client> allClients = getAll();
        for(Client client : allClients) {
            if(client.getClientId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        File file = new File(csvFilePath);
        if (!file.exists()) {
            return clients;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while((line = br.readLine()) != null) {
                // CSV line format: type,clientId,name,email,isRegistered,password,carModel,carLicensePlate,extraInfo1,extraInfo2,extraInfo3
                String[] parts = line.split(",");
                if(parts.length >= 8) {
                    String type = parts[0].trim();
                    String clientId = parts[1].trim();
                    String name = parts[2].trim();
                    String email = parts[3].trim();
                    boolean registered = Boolean.parseBoolean(parts[4].trim());
                    String password = parts[5].trim();
                    String carModel = parts[6].trim();
                    String carLicensePlate = parts[7].trim();

                    Car car = new Car(carModel, carLicensePlate);
                    Client client;

                    switch (type) {
                        case "Visitor":
                            String visitInfo = parts.length > 8 ? parts[8].trim() : "";
                            String visitorId = parts.length > 9 ? parts[9].trim() : "";
                            client = new Visitor(clientId, name, email, password, visitInfo, visitorId);
                            break;
                        case "FacultyMember":
                            String facultyId = parts.length > 8 ? parts[8].trim() : "";
                            String department = parts.length > 9 ? parts[9].trim() : "";
                            client = new FacultyMember(clientId, name, email, password, facultyId, department);
                            break;
                        case "NonFacultyStaff":
                            String staffId = parts.length > 8 ? parts[8].trim() : "";
                            String office = parts.length > 9 ? parts[9].trim() : "";
                            client = new NonFacultyStaff(clientId, name, email, password, staffId, office);
                            break;
                        case "Student":
                            String studentId = parts.length > 8 ? parts[8].trim() : "";
                            String major = parts.length > 9 ? parts[9].trim() : "";
                            String year = parts.length > 10 ? parts[10].trim() : "";
                            client = new Student(clientId, name, email, password, studentId, major, year);
                            break;
                        default:
                            continue;
                    }
                    client.setCar(car);
                    client.setRegistered(registered);
                    clients.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void save(Client obj) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // append a new line to the CSV
            String type = obj.getClass().getSimpleName();
            String line = type + "," +
                         obj.getClientId() + "," +
                         obj.getName() + "," +
                         obj.getEmail() + "," +
                         obj.isRegistered() + "," +
                         obj.getPassword() + "," +
                         (obj.getCar() != null ? obj.getCar().getModel() : "") + "," +
                         (obj.getCar() != null ? obj.getCar().getLicensePlate() : "");

            // Add extra info based on client type
            if (obj instanceof Visitor) {
                Visitor visitor = (Visitor) obj;
                line += "," + visitor.getvisitInformation() + "," + visitor.getVisitorId();
            } else if (obj instanceof FacultyMember) {
                FacultyMember faculty = (FacultyMember) obj;
                line += "," + faculty.getFacultyId() + "," + faculty.getDepartment();
            } else if (obj instanceof NonFacultyStaff) {
                NonFacultyStaff staff = (NonFacultyStaff) obj;
                line += "," + staff.getStaffId() + "," + staff.getOffice();
            } else if (obj instanceof Student) {
                Student student = (Student) obj;
                line += "," + student.getStudentId() + "," + student.getMajor() + "," + student.getYear();
            }

            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client updatedClient) {
        // 1. Read all existing clients
        List<Client> allClients = getAll();

        // 2. Replace the matching client
        for(int i = 0; i < allClients.size(); i++) {
            if(allClients.get(i).getClientId().equals(updatedClient.getClientId())) {
                allClients.set(i, updatedClient);
                break;
            }
        }

        // 3. Overwrite the entire CSV
        overwriteCSV(allClients);
    }

    @Override
    public void delete(String id) {
        List<Client> allClients = getAll();
        allClients.removeIf(client -> client.getClientId().equals(id));
        overwriteCSV(allClients);
    }

    private void overwriteCSV(List<Client> clients) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            for(Client c : clients) {
                String type = c.getClass().getSimpleName();
                String line = type + "," +
                            c.getClientId() + "," +
                            c.getName() + "," +
                            c.getEmail() + "," +
                            c.isRegistered() + "," +
                            c.getPassword() + "," +
                            (c.getCar() != null ? c.getCar().getModel() : "") + "," +
                            (c.getCar() != null ? c.getCar().getLicensePlate() : "");

                // Add extra info based on client type
                if (c instanceof Visitor) {
                    Visitor visitor = (Visitor) c;
                    line += "," + visitor.getvisitInformation() + "," + visitor.getVisitorId();
                } else if (c instanceof FacultyMember) {
                    FacultyMember faculty = (FacultyMember) c;
                    line += "," + faculty.getFacultyId() + "," + faculty.getDepartment();
                } else if (c instanceof NonFacultyStaff) {
                    NonFacultyStaff staff = (NonFacultyStaff) c;
                    line += "," + staff.getStaffId() + "," + staff.getOffice();
                } else if (c instanceof Student) {
                    Student student = (Student) c;
                    line += "," + student.getStudentId() + "," + student.getMajor() + "," + student.getYear();
                }

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}