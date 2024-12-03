import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestDriver {
    public static void main(String[] args) {
        System.out.println("Running tests...");
        
        // Run ParkingGarageTest
        Result result = JUnitCore.runClasses(ParkingGarageTest.class, ParkingTransactionTest.class, 
        		ReportGeneratorTest.class, EmployeeClientTest.class);

        // Print results
        for (Failure failure : result.getFailures()) {
            System.out.println("Test failed: " + failure.toString());
        }

        System.out.println("All tests successful: " + result.wasSuccessful());
    }
}
