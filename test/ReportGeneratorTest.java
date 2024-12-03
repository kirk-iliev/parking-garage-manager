import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorTest {
    private ParkingGarage garage;
    private ReportGenerator reportGenerator;

    @Before
    public void setUp() {
        garage = new ParkingGarage("G1", 2.5, 5);
        reportGenerator = new ReportGenerator();
    }

    @Test
    public void testGenerateReportEmptyGarage() {
        String report = reportGenerator.generateReport(garage);
        assertTrue("Report should show all spaces available", report.contains("Available Spaces: 5"));
        assertTrue("Report should show no occupied spaces", report.contains("Occupied Spaces: 0"));
        assertTrue("Report should indicate no transactions", report.contains("No transactions recorded."));
    }

    @Test
    public void testGenerateReportWithTransactions() {
        long entryTime = System.currentTimeMillis();
        garage.parkCar(entryTime);

        String report = reportGenerator.generateReport(garage);

        assertTrue("Report should show updated available spaces", report.contains("Available Spaces: 4"));
        assertTrue("Report should show updated occupied spaces", report.contains("Occupied Spaces: 1"));
        assertTrue("Report should include transaction details", report.contains("Transaction ID: T1"));
    }
}
