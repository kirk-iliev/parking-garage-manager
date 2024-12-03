import static org.junit.Assert.*;
import org.junit.Test;

public class ParkingTransactionTest {

    @Test
    public void testCalculateFee() {
        long entryTime = System.currentTimeMillis();
        long exitTime = entryTime + (90 * 60 * 1000); // 90 minutes later

        ParkingTransaction transaction = new ParkingTransaction("T1", entryTime);
        transaction.completeTransaction(exitTime, 2.5);

        assertEquals("Fee should be calculated correctly for partial hours", 3.75, transaction.getFee(), 0.01);
    }

    @Test
    public void testGetDurationInMinutes() {
        long entryTime = System.currentTimeMillis();
        long exitTime = entryTime + (120 * 60 * 1000); // 2 hours later

        ParkingTransaction transaction = new ParkingTransaction("T1", entryTime);
        transaction.completeTransaction(exitTime, 2.5);

        assertEquals("Duration should be calculated in minutes", 120, transaction.getDurationInMinutes());
    }

    @Test
    public void testCompleteTransactionInvalidExitTime() {
        long entryTime = System.currentTimeMillis();
        long invalidExitTime = entryTime - (30 * 60 * 1000); // 30 minutes earlier

        ParkingTransaction transaction = new ParkingTransaction("T1", entryTime);

        assertThrows(IllegalStateException.class, () -> {
            transaction.completeTransaction(invalidExitTime, 2.5);
        });
    }

    @Test
    public void testGetTransactionDetails() {
        long entryTime = System.currentTimeMillis();
        ParkingTransaction transaction = new ParkingTransaction("T1", entryTime);

        String details = transaction.getTransactionDetails();
        assertTrue("Transaction details should include the transaction ID", details.contains("Transaction ID: T1"));
        assertTrue("Transaction details should include the entry time", details.contains("Entry Time:"));
    }
}
