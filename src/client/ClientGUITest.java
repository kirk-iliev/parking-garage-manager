import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ClientGUITest {

	@Test
	void testClientGUI() {
		EmployeeClient eClient = new EmployeeClient("localhost", 12345);
		ClientGUI cGUI = new ClientGUI(eClient);
		Assert.assertNotNull(cGUI);
	}

}
