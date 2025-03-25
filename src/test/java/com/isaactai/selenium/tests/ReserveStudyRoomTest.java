package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.NeuLoginPage;
import com.isaactai.selenium.pages.SnellLibraryPage;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class ReserveStudyRoomTest extends BaseTest {

    private SnellLibraryPage snellLibraryPage;

    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Reserve Study Room Test triggered ===");
        test = extent.createTest("Reserve Study Room Scenario");

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        // Initialize the WebDriver instance here: ChromeDriver
        snellLibraryPage = new SnellLibraryPage(driver);
        driver.get("https://library.northeastern.edu/");
    }

    @Test
    public void testReserveStudyRoom() {
        logger.debug("=== Test Reserve Study Room triggered ===");

        try {
            snellLibraryPage.bookStudyRoom();

            NeuLoginPage neuLoginPage = new NeuLoginPage(driver);
            neuLoginPage.login("tai.hs", "fibtap-3xobho-zAxbyf$0");

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
}
