import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime; // import the LocalDate class

public class GUI {
public static void main(String[] args) throws IOException, ClassNotFoundException {
		
	    
	    
		String[] commands = {"Park Car",
				 	"Remove Car",
				 	"Generate Report",
				 	"Show Ticket Info",
				 	"Last Button",
				 	"Close"};
		 
		 int choice;
		 
		 do {
			 choice = JOptionPane.showOptionDialog(null,
					 "Select a command", 
					 "Parking Garage",
					 JOptionPane.YES_NO_CANCEL_OPTION, 
					 JOptionPane.QUESTION_MESSAGE, 
					 null, 
					 commands,
					 commands[commands.length - 1]);
		 
			 switch (choice) {
			 	case 0: doParkCar(); break;
			 	case 1: doRemoveCar(); break;
			 	case 2: doGenerateReport(); break;
			 	case 3: doShowTicketInfo(); break;
			 	case 4: doLastButton(); break;
			 	case 5: doClose(); break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
		
	}

	 


private static void doParkCar() {

	// Request the title
	String parkID = JOptionPane.showInputDialog("Enter Car Info");
	
    // Display current collection to the console for debugging
    JOptionPane.showMessageDialog(null,"Car Parked;");
}

private static void doRemoveCar() {

	// Request parking ticket info
	String ticketID = JOptionPane.showInputDialog("Enter transaction ID");
	
	// Convert string to integer
	int intID=Integer.parseInt(ticketID); 
	
	// Display fee
	double parkingFee = 0;
	
	
    JOptionPane.showMessageDialog(null, parkingFee);
}

private static void doGenerateReport() {

	// Return parking garage records
	String recordsList = null;
	JOptionPane.showMessageDialog(null, recordsList);

}


private static void doShowTicketInfo() {

	// Request parking ticket info
	String ticketID = JOptionPane.showInputDialog("Enter transaction ID");
	
	// Convert string to integer
	int intID=Integer.parseInt(ticketID); 
	
}


private static void doLastButton() {
            
   // Print total running time
   
   	
   JOptionPane.showMessageDialog(null, "test");
}


private static void doClose() {
	
	// Prompt user the program is closing
	   JOptionPane.showMessageDialog(null,"Closing Program");
	
}	

public void displayError(String string) {
	
	// display Error message
	   JOptionPane.showMessageDialog(null,string);
	
}	

public void updateStatus(String string) {
	
	// display status of connections
	   JOptionPane.showMessageDialog(null,string);
	
}	

public void displayMessage(String string) {
	
	// display message
	   JOptionPane.showMessageDialog(null,string);
	
}	

public void displayReport(String string) {
	
	// display message
	   JOptionPane.showMessageDialog(null,string);
	
}	

public String getEntryTimeInput() {
	
	// Return Entry Time
	String temp = null;
	
	return temp;
}	

public String getGarageIDInput(){
	
	// Return Entry Time
	String temp = null;
		
	return temp;
	
}

public String getTransactionIDInput(){
	
	// Return Entry Time
	String temp = null;
		
	return temp;
	
}

public String getExitTimeInput(){
	
	// Return Entry Time
	String temp = null;
		
	return temp;
	
}

}
