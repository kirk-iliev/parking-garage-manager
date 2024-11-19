import java.io.*;
import java.net.*;
import javax.swing.*;

public class EmployeeClient {

    // Attributes
    private GUI gui;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String serverAddress;
    private int serverPort;

    // Constructor
    public EmployeeClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.gui = new GUI(this);
        connectToServer();
    }

    // Connect to the server
    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            output = new PrintWriter(socket.getOutputStream(), true);
            gui.updateStatus("Connected to server");
        } catch (IOException e) {
            gui.displayError("Connection failed: " + e.getMessage());
        }
    }

    // Send a request to the server
    public void sendRequest(String request) {
        output.println(request);
    }

    // Receive and process the server's response
    public void receiveResponse() {
        try {
            String response = input.readLine();
            processResponse(response);
        } catch (IOException e) {
            gui.displayError("Failed to receive response: " + e.getMessage());
        }
    }

    // Process the server's response
    public void processResponse(String response) {
        if (response == null) {
            gui.displayError("No response received from server.");
            return;
        }

        String[] parts = response.split(":");
        String responseType = parts[0];

        switch (responseType) {
            case "PARK_CAR_SUCCESS":
                String transactionID = parts[1];
                gui.displayMessage("Car parked successfully. Transaction ID: " + transactionID);
                break;
            case "REMOVE_CAR_SUCCESS":
                String fee = parts[1];
                gui.displayMessage("Car removed. Parking fee: $" + fee);
                break;
            case "REPORT_DATA":
                String report = parts[1];
                gui.displayReport(report);
                break;
            case "ERROR":
                String errorMessage = parts[1];
                gui.displayError(errorMessage);
                break;
            default:
                gui.displayError("Unknown response from server.");
        }
    }

    // Disconnect from the server
    public void disconnect() {
        try {
            socket.close();
            input.close();
            output.close();
            gui.updateStatus("Disconnected from server");
        } catch (IOException e) {
            gui.displayError("Error during disconnection: " + e.getMessage());
        }
    }

    // Handle 'Park Car' action from GUI
    public void handleParkCar() {
        String entryTimeStr = gui.getEntryTimeInput();
        String garageID = gui.getGarageIDInput();

        // Convert entry time to milliseconds since epoch
        long entryTimeMillis = parseTime(entryTimeStr);
        if (entryTimeMillis == -1) {
            gui.displayError("Invalid entry time format.");
            return;
        }

        String request = "PARK_CAR:" + entryTimeMillis + ":" + garageID;
        sendRequest(request);
        receiveResponse();
    }

    // Handle 'Remove Car' action from GUI
    public void handleRemoveCar() {
        String transactionID = gui.getTransactionIDInput();
        String exitTimeStr = gui.getExitTimeInput();
        String garageID = gui.getGarageIDInput();

        long exitTimeMillis = parseTime(exitTimeStr);
        if (exitTimeMillis == -1) {
            gui.displayError("Invalid exit time format.");
            return;
        }

        String request = "REMOVE_CAR:" + transactionID + ":" + exitTimeMillis + ":" + garageID;
        sendRequest(request);
        receiveResponse();
    }

    // Handle 'Generate Report' action from GUI
    public void handleGenerateReport() {
        String garageID = gui.getGarageIDInput();
        String request = "GENERATE_REPORT:" + garageID;
        sendRequest(request);
        receiveResponse();
    }

    // Parse time from string to milliseconds
    private long parseTime(String timeStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
            return -1;
        }
    }
}
