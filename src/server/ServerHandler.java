import java.io.*;
import java.net.*;
import java.util.*;

public class ServerHandler {

    // Attributes
    private ServerSocket serverSocket;
    private List<Socket> clientSockets;
    private Map<String, ParkingGarage> parkingGarages;

    // Constructor
    public ServerHandler(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSockets = new ArrayList<>();
            parkingGarages = new HashMap<>();
            initializeParkingGarages();
            System.out.println("Server initialized on port: " + port);
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            serverSocket = null; // Explicitly set to null for clarity
        }
    }
    

    // Initialize parking garages 
    private void initializeParkingGarages() {
        
        parkingGarages.put("G1", new ParkingGarage("G1", 2.5, 100));
        parkingGarages.put("G2", new ParkingGarage("G2", 3.0, 5));
    }

    // Start accepting client connections
    public void startServer() {
        if (serverSocket == null) {
            System.err.println("Cannot start server: ServerSocket is not initialized.");
            return;
        }
        System.out.println("Server started. Waiting for clients...");
        acceptConnections();
    }
    

    // Accept new client connections
    private void acceptConnections() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new Thread(() -> handleClientRequest(clientSocket)).start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    // Handle client requests
    private void handleClientRequest(Socket clientSocket) {
        try (
            BufferedReader input = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                clientSocket.getOutputStream(), true
            )
        ) {
            String request;
            while ((request = input.readLine()) != null) {
                processRequest(request, output);
            }
        } catch (IOException e) {
            System.err.println("Connection error with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                clientSockets.remove(clientSocket);
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    // Process incoming requests
    private void processRequest(String request, PrintWriter output) {
        if (request == null || request.trim().isEmpty()) {
            output.println("ERROR:Empty or null request");
            return;
        }
        String[] parts = request.split(":");
        if (parts.length < 2) {
            output.println("ERROR:Invalid request format");
            return;
        }
        String action = parts[0];
        switch (action) {
            case "PARK_CAR":
                handleParkCarRequest(parts, output);
                break;
            case "REMOVE_CAR":
                handleRemoveCarRequest(parts, output);
                break;
            case "GENERATE_REPORT":
                handleGenerateReportRequest(parts, output);
                break;
            default:
                output.println("ERROR:Invalid action");
        }
    }
    

    // Handle 'PARK_CAR' request
    private void handleParkCarRequest(String[] parts, PrintWriter output) {
        try {
            String entryTimeStr = parts[1];
            String garageID = parts[2];
            long entryTimeMillis = Long.parseLong(entryTimeStr);
    
            ParkingGarage garage = getGarageByID(garageID);
            if (garage != null) {
            	
            	if (garage.getAvailableSpace() <= 0) {
            		output.println("ERROR:Garage is full");
            		return;
            	}
                String transactionID = garage.parkCar(entryTimeMillis);
                output.println("PARK_CAR_SUCCESS:" + transactionID);
            } else {
                output.println("ERROR:Garage not found");
            }
        } catch (Exception e) {
            output.println("ERROR:" + e.getMessage());
        }
    }
    


    // Handle 'REMOVE_CAR' request
    private void handleRemoveCarRequest(String[] parts, PrintWriter output) {
        try {
            String transactionID = parts[1];
            String exitTimeStr = parts[2];
            String garageID = parts[3];
            long exitTimeMillis = Long.parseLong(exitTimeStr);
    
            ParkingGarage garage = getGarageByID(garageID);
            if (garage != null) {
                boolean success = garage.removeCar(transactionID, exitTimeMillis);
                if (success) {
                    ParkingTransaction transaction = garage.getTransaction(transactionID);
                    double fee = transaction.getFee();
                    output.println("REMOVE_CAR_SUCCESS:" + fee);
                } else {
                    output.println("ERROR:Transaction not found or already completed");
                }
            } else {
                output.println("ERROR:Garage not found");
            }
        } catch (Exception e) {
            output.println("ERROR:" + e.getMessage());
        }
    }
    


    // Handle 'GENERATE_REPORT' request
    private void handleGenerateReportRequest(String[] parts, PrintWriter output) {
        try {
            String garageID = parts[1];
            ParkingGarage garage = getGarageByID(garageID);
    
            if (garage != null) {
                ReportGenerator reportGenerator = new ReportGenerator();
                String report = reportGenerator.generateReport(garage);
                System.out.println("Generated Report: " + report); // Debugging
                output.println(report); // Send the report
                output.println("END_REPORT"); // End marker
                output.flush();
            } else {
                output.println("ERROR:Garage not found");
                output.flush();
            }
        } catch (Exception e) {
            output.println("ERROR:" + e.getMessage());
            output.flush();
        }
    }
    

    // Retrieve a ParkingGarage by ID
    private ParkingGarage getGarageByID(String garageID) {
        return parkingGarages.get(garageID);
    }

    // Stop the server and close all connections
    public void stopServer() {
        try {
            serverSocket.close();
            for (Socket clientSocket : clientSockets) {
                clientSocket.close();
            }
            clientSockets.clear();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 12345; // Port number for the server
        ServerHandler server = new ServerHandler(port);
        server.startServer();
    }
    
}
