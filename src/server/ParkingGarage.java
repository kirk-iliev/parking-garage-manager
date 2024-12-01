import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime; // import the LocalDate class

public class ParkingGarage {
	
	static private int count = 0;      // Use for garage ID
	
	// All class attributes
	/**
	private int garageID;
	*/
	private String garageID;
	private int totalSpaces;
	private int occupiedSpaces;
	private double feeRate;
	private int availableSpaces;
	private int activeTransactions;
	private int numOfTransactions;
	
	// Store transaction data for garage 
	private ParkingTransaction[] transactionList;

	
	public ParkingGarage(String id, double rate, int spaceNum){
		
		/**
		this.garageID = count + 1;
		*/
		this.garageID = id;
		this.totalSpaces = spaceNum;
		this.availableSpaces = spaceNum;
		this.occupiedSpaces = 0;
		this.feeRate = rate;
		this.activeTransactions = 0;
		this.numOfTransactions = 0;
		
		// Create initial list of transactions
		transactionList = new ParkingTransaction[100];

	}
	
	public void parkCar() {
		
		this.numOfTransactions++;
		LocalDateTime currentTime = LocalDateTime.now();
		transactionList[numOfTransactions-1] = new ParkingTransaction(numOfTransactions, currentTime);
		this.occupiedSpaces++;
		this.availableSpaces--;
	}
	
	public void removeCar(int transactionID) {
		
		double tempFee;
		LocalDateTime currentTime = LocalDateTime.now();
		
		transactionList[transactionID+1].completeTransaction(currentTime, getFeeRate());
		
		// Adjust garage capacity information
		this.occupiedSpaces--;
		this.availableSpaces++;
		
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
	
	public String displayTransactions() {
		
		String displayString = null;
		for (int i = 0; i < this.numOfTransactions; i++) {
			displayString = transactionList[i].getTransactionDetails();
			displayString = displayString + "\n";
		}
		
		return displayString;
	}

}
