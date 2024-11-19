import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingTransaction {
	
    private String transactionID;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;
    private boolean isActive;

    public ParkingTransaction(String transactionID, LocalDateTime entryTime) {
        this.transactionID = transactionID;
        this.entryTime = entryTime;
        this.isActive = true; 
        this.fee = 0.0;       
    }

    public void completeTransaction(LocalDateTime exitTime, double feeRate) {
        if (!isActive) {
            System.out.println("Transaction is already completed.");
            return;
        }
        this.exitTime = exitTime;
        this.fee = calculateFee(feeRate);
        this.isActive = false; 
    }

    public double calculateFee(double feeRate) {
        long duration = getDuration(); 
        return Math.ceil(duration / 60.0) * feeRate; 
    }

    public long getDuration() {
        if (exitTime == null) {
            System.out.println("Exit time not set. Transaction is still active.");
            return 0;
        }
        return Duration.between(entryTime, exitTime).toMinutes();
    }

    // Method to get transaction details as a formatted string
    public String getTransactionDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder details = new StringBuilder();
        details.append("Transaction ID: ").append(transactionID).append("\n");
        details.append("Entry Time: ").append(entryTime.format(formatter)).append("\n");

        if (exitTime != null) {
            details.append("Exit Time: ").append(exitTime.format(formatter)).append("\n");
        } else {
            details.append("Exit Time: Not set (transaction still active)\n");
        }

        details.append("Fee: $").append(String.format("%.2f", fee)).append("\n");
        details.append("Active: ").append(isActive ? "Yes" : "No").append("\n");
        return details.toString();
    }

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public boolean isActive() {
        return isActive;
    }  
}
