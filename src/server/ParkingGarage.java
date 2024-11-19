import java.io.*;
import java.util.Scanner;
import java.time.LocalDate; // import the LocalDate class

public class ParkingGarage {
	
	static private int count = 0;      // Use for garage ID
	
	// All class attributes
	private int garageID;
	private int totalSpaces;
	private int occupiedSpaces;
	private double feeRate;
	private int availableSpaces;
	private int activeTransactions;
	private int numOfTransactions;
	
	// Store transaction data for garage 
	private ParkingTransactions[] transactionList;

	
	public ParkingGarage(int spaceNum, double rate){
		
		this.garageID = count + 1;
		this.totalSpaces = spaceNum;
		this.availableSpaces = spaceNum;
		this.occupiedSpaces = 0;
		this.feeRate = rate;
		this.activeTransactions = 0;
		this.numOfTransactions = 0;
		 
		transactionList = new ParkingTransactions[10];

	}
	
	public void parkCar() {
		
		// Need programming if transaction list is full	
		
		LocalDate currentTime = LocalDate.now();
		transactionList[numOfTransactions] = new ParkingTransaction(currentTime);
		this.numOfTransactions++;
		this.occupiedSpaces++;
		this.availableSpaces--;
	}
	
	public double removeCar(int transactionID) {
		
		double tempFee;
		LocalDate currentTime = LocalDate.now();
		
		tempFee = transactionlist[transactionID+1].completeTransaction(transactionID, currentTime, getFeeRate());
		
		// Adjust garage capacity information
		this.occupiedSpaces--;
		this.availableSpaces++;
		
		return tempFee;
		
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

}
