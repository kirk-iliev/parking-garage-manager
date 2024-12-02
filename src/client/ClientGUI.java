import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {

    private EmployeeClient client; // Reference to EmployeeClient

    // GUI components
    private JTextField entryTimeField;
    private JTextField exitTimeField;
    private JTextField transactionIDField;
    private JTextField garageIDField;
    private JTextArea outputArea;

    public ClientGUI(EmployeeClient client) {
        this.client = client;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Parking Garage Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input fields
        entryTimeField = new JTextField();
        exitTimeField = new JTextField();
        transactionIDField = new JTextField();
        garageIDField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Fields"));
        inputPanel.add(new JLabel("Entry Time (yyyy-MM-dd HH:mm):"));
        inputPanel.add(entryTimeField);
        inputPanel.add(new JLabel("Exit Time (yyyy-MM-dd HH:mm):"));
        inputPanel.add(exitTimeField);
        inputPanel.add(new JLabel("Transaction ID:"));
        inputPanel.add(transactionIDField);
        inputPanel.add(new JLabel("Garage ID:"));
        inputPanel.add(garageIDField);
        add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton parkCarButton = new JButton("Park Car");
        JButton removeCarButton = new JButton("Remove Car");
        JButton generateReportButton = new JButton("Generate Report");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(parkCarButton);
        buttonPanel.add(removeCarButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Button actions
        parkCarButton.addActionListener(event -> client.handleParkCar());
        removeCarButton.addActionListener(event -> client.handleRemoveCar());
        generateReportButton.addActionListener(event -> client.handleGenerateReport());
        exitButton.addActionListener(event -> client.disconnect());
    }

    // Getters for input fields
    public String getEntryTimeInput() {
        return entryTimeField.getText().trim();
    }

    public String getExitTimeInput() {
        return exitTimeField.getText().trim();
    }

    public String getTransactionIDInput() {
        return transactionIDField.getText().trim();
    }

    public String getGarageIDInput() {
        return garageIDField.getText().trim();
    }

    // Display messages in output area
    public void displayMessage(String message) {
        outputArea.append("SUCCESS: " + message + "\n");
    }

    public void displayError(String error) {
        outputArea.append("ERROR: " + error + "\n");
    }

    public void displayReport(String report) {
        outputArea.append("---- Report ----\n" + report + "\n----------------\n");
    }

    public void updateStatus(String status) {
        outputArea.append("STATUS: " + status + "\n");
    }
}
