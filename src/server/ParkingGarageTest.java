import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime; // import the LocalDate class

public class ParkingGarageTest {

	@Test
	void testParkingGarageInit() {
		ParkingGarage garage = new ParkingGarage("G1",2.5,10);
		Assert.assertNotNull(garage.getGarageID());
	}

	@Test
	void testParkCarTransaction() {
		ParkingGarage garage = new ParkingGarage("G1",2.5,10);
		LocalDateTime currentTime = LocalDateTime.now();
		garage.parkCar(currentTime);
		Assert.assertTrue(garage.getNumOfTransactions() == 1);
		
	}

	@Test
	void testRemoveCar() {
		ParkingGarage garage = new ParkingGarage("G1",2.5,10);
		LocalDateTime currentTime = LocalDateTime.now();
		garage.parkCar(currentTime);
		garage.parkCar(currentTime);
		garage.removeCar("T1", currentTime);
		Assert.assertTrue(garage.getOccupiedSpaces() == 1);
	}

}
