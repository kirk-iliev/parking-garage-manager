import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime; // import the LocalDate class

class ParkingTransactionTest {

	@Test
	void testParkingTransaction() {
		LocalDateTime currentTime = LocalDateTime.now();
		ParkingTransaction transaction = new ParkingTransaction("T1", currentTime);
		Assert.assertNotNull(transaction);
	}

	@Test
	void testCompleteTransaction() {
		LocalDateTime currentTime = LocalDateTime.now();
		ParkingTransaction transaction = new ParkingTransaction("T1", currentTime);
		transaction.completeTransaction(currentTime, 1.5);
		Assert.assertFalse(transaction.isActive());
	}

	@Test
	void testTrasactionDuration() {
		LocalDateTime currentTime = LocalDateTime.now();
		ParkingTransaction transaction = new ParkingTransaction("T1", currentTime);
		LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(5);
		transaction.completeTransaction(fiveMinutesLater, 1.5);
		Assert.assertTrue(transaction.getDuration() == 5);
	}

	@Test
	void testGetFee() {
		LocalDateTime currentTime = LocalDateTime.now();
		ParkingTransaction transaction = new ParkingTransaction("T1", currentTime);
		LocalDateTime hourLater = LocalDateTime.now().plusMinutes(60);
		transaction.completeTransaction(hourLater, 1.5);
		Assert.assertTrue(transaction.calculateFee(1.5) == 1.5);
	}

}
