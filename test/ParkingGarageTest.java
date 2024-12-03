import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParkingGarageTest {
    private ParkingGarage garage;

    @Before
    public void setUp() {
        garage = new ParkingGarage("G1", 2.5, 10); // Garage ID: G1, Fee Rate: $2.50/hour, 10 spaces
    }

    @Test
    public void testParkCar() {
        long entryTime = System.currentTimeMillis();
        String transactionID = garage.parkCar(entryTime);

        assertNotNull("Transaction ID should not be null", transactionID);
        assertEquals("Available spaces should decrease by 1", 9, garage.getAvailableSpace());
        assertEquals("Occupied spaces should increase by 1", 1, garage.getOccupiedSpaces());
        assertEquals("Number of transactions should increase by 1", 1, garage.getNumOfTransactions());
    }

    @Test
    public void testRemoveCar() {
        long entryTime = System.currentTimeMillis();
        String transactionID = garage.parkCar(entryTime);

        long exitTime = entryTime + (2 * 60 * 60 * 1000); // 2 hours later
        boolean success = garage.removeCar(transactionID, exitTime);

        assertTrue("Car should be successfully removed", success);
        assertEquals("Available spaces should increase by 1", 10, garage.getAvailableSpace());
        assertEquals("Occupied spaces should decrease by 1", 0, garage.getOccupiedSpaces());
    }

    @Test
    public void testRemoveCarInvalidTransaction() {
        long exitTime = System.currentTimeMillis();
        boolean success = garage.removeCar("InvalidID", exitTime);

        assertFalse("Removing a car with an invalid transaction ID should fail", success);
    }
}
