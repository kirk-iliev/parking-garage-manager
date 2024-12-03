import java.util.List;

public class ReportGenerator {

    // Generate report for a specific parking garage
    public String generateReport(ParkingGarage garage) {
        StringBuilder report = new StringBuilder();
        report.append("---- Parking Garage Report ----\n");
        report.append("Garage ID: ").append(garage.getGarageID()).append("\n");
        report.append("Total Spaces: ").append(garage.getTotalSpaces()).append("\n");
        report.append("Available Spaces: ").append(garage.getAvailableSpace()).append("\n");
        report.append("Occupied Spaces: ").append(garage.getOccupiedSpaces()).append("\n");
        report.append("Number of Active Transactions: ").append(garage.getActiveTransactions()).append("\n");
        report.append("Number of Completed Transactions: ").append(garage.getNumOfTransactions()).append("\n");

        // Append transaction details
        report.append("\n---- Transaction Details ----\n");
        List<ParkingTransaction> transactions = garage.getTransactionList();
        if (transactions.isEmpty()) { // Check if the list is empty
            report.append("No transactions recorded.\n");
        } else {
            for (ParkingTransaction transaction : transactions) {
                report.append(transaction.getTransactionDetails()).append("\n");
            }
        }
        report.append("-----------------------------\n");

        return report.toString();
    }
}
