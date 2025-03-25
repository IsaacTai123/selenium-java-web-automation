package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.NeuLoginPage;
import com.isaactai.selenium.pages.SnellLibraryPage;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.isaactai.selenium.utils.ExcelUtil;

/**
 * @author tisaac
 */
public class ReserveStudyRoomTest extends BaseTest {

    private SnellLibraryPage snellLibraryPage;
    private String testName;
    private String snellLibraryUrl;
    private String neuUsername;
    private String neuPassword;

    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Reserve Study Room Test triggered ===");
        loadReserveStudyRoomTestData();
        test = extent.createTest(testName);

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        // Initialize the WebDriver instance here: ChromeDriver
        snellLibraryPage = new SnellLibraryPage(driver);
        driver.get(snellLibraryUrl);
    }

    @Test
    public void testReserveStudyRoom() {
        logger.debug("=== Test Reserve Study Room triggered ===");

        try {
            snellLibraryPage.bookStudyRoom();

            NeuLoginPage neuLoginPage = new NeuLoginPage(driver);
            neuLoginPage.login(neuUsername, neuPassword);

            snellLibraryPage.confirmBooking();

            test.info("Expected: Booking confirmation page");
            test.info("Actual: Booking confirmation page showed up");

            Assert.assertTrue(snellLibraryPage.isBookingConfirmed(), "Booking confirmation page did not show up");
            test.pass("Successfully booked a study room");
        } catch (Exception e) {
            test.fail("failed to book a study room");
            Assert.fail("Booking confirmation page did not show up", e);
        }
    }

    public void loadReserveStudyRoomTestData() {
        String excelSheetName = "ReserveStudyRoomTest";
        testName = ExcelUtil.getCellValue(excelSheetName, "testName", "TestData");
        snellLibraryUrl = ExcelUtil.getCellValue(excelSheetName, "snellLibraryUrl", "TestData");

        String shareSheetName = "ShareData";
        neuUsername = ExcelUtil.getCellValue(shareSheetName, "neuUsername", "TestData");
        neuPassword = ExcelUtil.getCellValue(shareSheetName, "neuPassword", "TestData");

    }
}
