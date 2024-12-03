import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
public class EmployeeClientTest {
	private ServerHandler server = new ServerHandler( 8000 );
	
	@Test
	public void testConstructor()
	{
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			int port = 8000;
			EmployeeClient client = new EmployeeClient( address, port );
			assertNotNull( client );
			assertEquals( "connected", client.getStatus() );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDisconnect()
	{
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			int port = 8000;
			EmployeeClient client = new EmployeeClient( address, port );
			client.disconnect();
			assertEquals( "disconnected", client.getStatus() );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		server.stopServer();
	}
}

