import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientGUI {
public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		/**
		Scanner sc= new Scanner(System.in); //System.in is a standard input stream.

	    // Request port and host from user
	    System.out.print("Enter the port number to connect to: <7777> ");
	    int port = sc.nextInt();
	    sc.nextLine();
	    System.out.print("Enter the host address to connect to: <localhost> ");
	    String host = sc.nextLine();
	    
	    // Connect to the ServerSocket at host:port
	    Socket socket = new Socket(host, port);
	    System.out.println("Connected to " + host + ":" + port);
	    
	    
	    // create a input and output so we can read and send data
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
	    
	    
	    // Send login message
	    Message loginMsg = new Message("Login");
	    objectOutputStream.writeObject(loginMsg);
	    
	    // Check for success message and print to user
	    Message inputMsg = (Message)objectInputStream.readObject();
	    printMessage(inputMsg);
	    */
	    
	    
		String[] commands = {"Park Car",
				 	"Remove Car",
				 	"Access Record",
				 	"Show Ticket Info",
				 	"Last Button",
				 	"Close"};
		 
		 int choice;
		 
		 do {
			 choice = JOptionPane.showOptionDialog(null,
					 "Select a command", 
					 "DVD Collection",
					 JOptionPane.YES_NO_CANCEL_OPTION, 
					 JOptionPane.QUESTION_MESSAGE, 
					 null, 
					 commands,
					 commands[commands.length - 1]);
		 
			 switch (choice) {
			 	case 0: doParkCar(); break;
			 	case 1: doRemoveCar(); break;
			 	case 2: doAccessRecord(); break;
			 	case 3: doShowTicketInfo(); break;
			 	case 4: doLastButton(); break;
			 	case 5: doClose(); break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
		 
		 /**
		 objectInputStream.close();
		 objectOutputStream.close();
		 sc.close();
		 socket.close();
		 */
	}

	 


private static void doParkCar() {

	// Request the title
	String title = JOptionPane.showInputDialog("\n\nEnter Car Info");
	
	
   
           
    // Display current collection to the console for debugging
    JOptionPane.showMessageDialog(null,"Car Parked;");
}

private static void doRemoveCar() {

	// Request the title

	String title = JOptionPane.showInputDialog("Enter title");
	if (title == null) {
		return;		// dialog was cancelled
	}
	title = title.toUpperCase();
	
           // Remove the matching DVD if found
           
           
           // Display current collection to the console for debugging
           JOptionPane.showMessageDialog(null, "Removing " + title + "\n");
}

private static void doAccessRecord() {

	// Request the title
	String title = JOptionPane.showInputDialog("\n\nEnter Title");
	if (title == null) {
		return;		// dialog was cancelled
	}
	title = title.toUpperCase();
	
	String info = "null";
	
	// String to hold image file name
	String imageLoad = title.replaceAll("\\s", "");
	imageLoad = imageLoad + ".jpg";
	
	/**
	ImageIcon icon = new ImageIcon(ClientGUI.class.getResource(imageLoad));
    JOptionPane.showMessageDialog(
           null,
           info,
           "DVD", JOptionPane.INFORMATION_MESSAGE,
           icon);
           */

}


private static void doShowTicketInfo() {

	// Request the rating
	String rating = JOptionPane.showInputDialog("Enter rating");
	if (rating == null) {
		return;		// dialog was cancelled
	}
	rating = rating.toUpperCase();
	
           String results = "null";
           
           JOptionPane.showMessageDialog(null, "DVDs with rating " + rating + results );

}


private static void doLastButton() {
            
   // Print total running time
   
   	
   JOptionPane.showMessageDialog(null, "Total Running Time of DVDs");
}


private static void doClose() {
	// Display current collection to the console for debugging
	   JOptionPane.showMessageDialog(null,"Closing Program");
	
}	
	

private static void printMessage(Message msg){
    System.out.println("\nStatus: " + msg.getStatus());
    System.out.println("Text: " + msg.getText());
}

}
