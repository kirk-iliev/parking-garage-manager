import java.util.ArrayList;
import java.util.List;

public class ParkingGarage {
	
	// All class attributes
	private int garageID;
	private int totalSpaces;
	private int occupiedSpaces;
	private double feeRate;
	private int availableSpaces;
	//private int activeTransactions;
	private int numOfTransactions;
	
	// Store transaction data for garage 
	private ParkingTransaction[] transactionList;

	
	public ParkingGarage(String garageID, double rate, int spaceNum) {
		this.garageID = Integer.parseInt(garageID.substring(1)); // Extract number from "G1", "G2"
		this.totalSpaces = spaceNum;
		this.availableSpaces = spaceNum;
		this.occupiedSpaces = 0;
		this.feeRate = rate;
		//this.activeTransactions = 0;
		this.numOfTransactions = 0;
	
		// Create initial list of transactions
		transactionList = new ParkingTransaction[100];
	}
	
	
	public String parkCar(long entryTimeMillis) {
		if (numOfTransactions >= transactionList.length) {
			System.err.println("Transaction list is full.");
			return null; // Handle full transaction list
		}
		String transactionID = "T" + (numOfTransactions + 1);
		transactionList[numOfTransactions] = new ParkingTransaction(transactionID, entryTimeMillis);
		numOfTransactions++;
		occupiedSpaces++;
		availableSpaces--;
		return transactionID;
	}
	
	
	public boolean removeCar(String transactionID, long exitTimeMillis) {
		for (int i = 0; i < numOfTransactions; i++) {
			ParkingTransaction transaction = transactionList[i];
			if (transaction != null && transaction.getTransactionID().equals(transactionID)) {
				if (!transaction.isActive()) {
					System.err.println("Transaction is already completed.");
					return false;
				}
				transaction.completeTransaction(exitTimeMillis, feeRate);
				occupiedSpaces--;
				availableSpaces++;
				return true;
			}
		}
		System.err.println("Transaction ID not found.");
		return false;
	}
	
	
	public int getTotalSpaces() {
		return this.totalSpaces;
		
	}
	
	public int getAvailableSpace() {
		return this.availableSpaces;
		
	}
	
	public int getOccupiedSpaces() {
		return this.occupiedSpaces;
		
	}
	
	public double getFeeRate() {
		return this.feeRate;
		
	}

	// In ParkingGarage class
	public List<ParkingTransaction> getTransactionList() {
		List<ParkingTransaction> activeTransactions = new ArrayList<>();
		for (ParkingTransaction transaction : transactionList) {
			if (transaction != null) {
				activeTransactions.add(transaction);
			}
		}
		return activeTransactions;
	}


	public int getGarageID() {
    	return this.garageID;
	}

	public int getNumOfTransactions() {
		return this.numOfTransactions;
	}
	
	
	public int getActiveTransactions() {
        int activeCount = 0;
        for (ParkingTransaction transaction : transactionList) {
            if (transaction != null && transaction.isActive()) {
                activeCount++;
            }
        }
        return activeCount;
    }

	public ParkingTransaction getTransaction(String transactionID) {
		for (ParkingTransaction transaction : transactionList) {
			if (transaction != null && transaction.getTransactionID().equals(transactionID)) {
				return transaction;
			}
		}
		return null; // Return null if no matching transaction is found
	}

}
