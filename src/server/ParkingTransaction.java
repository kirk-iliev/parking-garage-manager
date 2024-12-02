import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ParkingTransaction {

    private String transactionID;
    private long entryTime; 
    private long exitTime; 

    private double fee;
    private boolean isActive;

    // Constructor to initialize the transaction
    public ParkingTransaction(String transactionID, long entryTime) {
        this.transactionID = transactionID;
        this.entryTime = entryTime;
        this.isActive = true; 
        this.fee = 0.0;       
    }

    // Method to complete the transaction
    public void completeTransaction(long exitTime, double feeRate) {
        if (!isActive) {
            System.out.println("Transaction is already completed.");
            return;
        }
        this.exitTime = exitTime;
        this.fee = calculateFee(feeRate);
        this.isActive = false; 
    }

    // Method to calculate the fee based on the duration and fee rate
    public double calculateFee(double feeRate) {
        long durationInMinutes = getDurationInMinutes();
        double durationInHours = durationInMinutes / 60.0; // Convert minutes to hours
        return durationInHours * feeRate; // Round up to the nearest hour
    }

    // Method to calculate the duration in minutes
    public long getDurationInMinutes() {
        if (exitTime <= entryTime) {
            throw new IllegalStateException("Invalid duration: Exit time must be after entry time.");
        }
        return (exitTime - entryTime) / (1000 * 60); // Convert milliseconds to minutes
    }

    // Method to get transaction details as a formatted string
    public String getTransactionDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Transaction ID: ").append(transactionID).append("\n");
        details.append("Entry Time: ").append(formatTime(entryTime)).append("\n");

        if (isActive) {
            details.append("Exit Time: Not set (transaction still active)\n");
        } else {
            details.append("Exit Time: ").append(formatTime(exitTime)).append("\n");
        }

        details.append("Fee: $").append(String.format("%.2f", fee)).append("\n");
        details.append("Active: ").append(isActive ? "Yes" : "No").append("\n");
        return details.toString();
    }

    // Helper method to format epoch time to human-readable string
    private String formatTime(long timeMillis) {
        LocalDateTime dateTime = java.time.Instant.ofEpochMilli(timeMillis)
                                                  .atZone(ZoneId.systemDefault())
                                                  .toLocalDateTime();
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    // Getters
    public String getTransactionID() {
        return transactionID;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public boolean isActive() {
        return isActive;
    }
}
