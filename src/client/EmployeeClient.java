import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EmployeeClient {

    // Attributes
    private ClientGUI gui;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String serverAddress;
    private int serverPort;

    // Constructor
    public EmployeeClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.gui = new ClientGUI(this);
        connectToServer();
    }

    // Connect to the server
    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            gui.updateStatus("Connected to server");
        } catch (IOException e) {
            gui.displayError("Connection failed: " + e.getMessage());
        }
    }

    // Send a request to the server
    public void sendRequest(String request) {
        if (socket == null || socket.isClosed()) {
            gui.displayError("Not connected to the server.");
            return;
        }
        output.println(request);
    }

    // Receive and process the server's response
    public void receiveResponse() {
        try {
            String response = input.readLine();
            if (response == null) {
                gui.displayError("No response from server.");
                return;
            }
            processResponse(response);
        } catch (IOException e) {
            gui.displayError("Failed to receive response: " + e.getMessage());
        }
    }

    // Process the server's response
    public void processResponse(String response) {
        String[] parts = response.split(":");
        String responseType = parts[0];

        switch (responseType) {
            case "PARK_CAR_SUCCESS":
                gui.displayMessage("Car parked successfully. Transaction ID: " + parts[1]);
                break;
            case "REMOVE_CAR_SUCCESS":
                gui.displayMessage("Car removed successfully. Parking fee: $" + parts[1]);
                break;
            case "REPORT_DATA":
                gui.displayReport(parts[1]);
                break;
            case "ERROR":
                gui.displayError(parts[1]);
                break;
            default:
                gui.displayError("Unknown response type: " + responseType);
        }
    }

    // Disconnect from the server
    public void disconnect() {
        try {
            if (socket != null) socket.close();
            if (input != null) input.close();
            if (output != null) output.close();
            gui.updateStatus("Disconnected from server");
        } catch (IOException e) {
            gui.displayError("Error during disconnection: " + e.getMessage());
        }
    }

    // Handle 'Park Car' action from GUI
    public void handleParkCar() {
        try {
            String entryTimeStr = gui.getEntryTimeInput();
            String garageID = gui.getGarageIDInput();

            if (entryTimeStr.isEmpty() || garageID.isEmpty()) {
                gui.displayError("Entry Time and Garage ID are required.");
                return;
            }

            long entryTimeMillis = parseTime(entryTimeStr);
            String request = "PARK_CAR:" + entryTimeMillis + ":" + garageID;
            sendRequest(request);
            receiveResponse();
        } catch (ParseException e) {
            gui.displayError("Invalid entry time format.");
        } catch (Exception e) {
            gui.displayError("Error handling 'Park Car': " + e.getMessage());
        }
    }

    // Handle 'Remove Car' action from GUI
    public void handleRemoveCar() {
        try {
            String transactionID = gui.getTransactionIDInput();
            String exitTimeStr = gui.getExitTimeInput();
            String garageID = gui.getGarageIDInput();

            if (transactionID.isEmpty() || exitTimeStr.isEmpty() || garageID.isEmpty()) {
                gui.displayError("Transaction ID, Exit Time, and Garage ID are required.");
                return;
            }

            long exitTimeMillis = parseTime(exitTimeStr);
            String request = "REMOVE_CAR:" + transactionID + ":" + exitTimeMillis + ":" + garageID;
            sendRequest(request);
            receiveResponse();
        } catch (ParseException e) {
            gui.displayError("Invalid exit time format.");
        } catch (Exception e) {
            gui.displayError("Error handling 'Remove Car': " + e.getMessage());
        }
    }

    // Handle 'Generate Report' action from GUI
    public void handleGenerateReport() {
        try {
            String garageID = gui.getGarageIDInput();
    
            if (garageID.isEmpty()) {
                gui.displayError("Garage ID is required.");
                return;
            }
    
            String request = "GENERATE_REPORT:" + garageID;
            sendRequest(request);
    
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while (!(line = input.readLine()).equals("END_REPORT")) {
                responseBuilder.append(line).append("\n");
            }
            String report = responseBuilder.toString();
    
            gui.displayReport(report); // Display the report in the GUI
        } catch (IOException e) {
            gui.displayError("Error handling 'Generate Report': " + e.getMessage());
        }
    }
    
    // Parse time from string to milliseconds
    private long parseTime(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(timeStr);
        return date.getTime();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the server IP address: ");
        String serverAddress = scanner.nextLine().trim();
        
        System.out.print("Enter the server port (default 12345): ");
        String portInput = scanner.nextLine().trim();
        int serverPort = portInput.isEmpty() ? 12345 : Integer.parseInt(portInput);
        
        EmployeeClient client = new EmployeeClient(serverAddress, serverPort);
        client.gui.setVisible(true);
        scanner.close();
    }
}
