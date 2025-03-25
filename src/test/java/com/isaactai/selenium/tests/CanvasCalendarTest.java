package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.CanvasPage;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class CanvasCalendarTest extends BaseTest {

    private CanvasPage canvasPage;
    private final By canvasLogin = By.cssSelector("#menu-item-menu-main-desktop-4343 a");

    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Canvas Calendar Test triggered ===");
        test = extent.createTest("Canvas Calendar Scenario");


        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get("https://canvas.northeastern.edu");
        microsoftLoginPage.click(canvasLogin);
        microsoftLoginPage.login("tai.hs@northeastern.edu", "fibtap-3xobho-zAxbyf$0");

        canvasPage = new CanvasPage(driver);
    }

    @Test
    public void testCanvasCalendar() {
        try {
            logger.debug("=== Test Canvas Calendar triggered ===");

            canvasPage.clickCalendarBtn();
            canvasPage.clickCreateNewEventBtn();
            canvasPage.createNewEvent("Test Event", "Wed, Mar 26, 2025", "10:00 AM", "11:30 AM", "Does not repeat", "Northeastern Curry Student Center");

            test.info("Expected: Test Event appear on the calendar");
            test.info("Actual: " +  (canvasPage.isEventDisplayed("Test Event")
                    ? "Event found on the calendar"
                    : "Event not found on the calendar"));

            Assert.assertTrue(canvasPage.isEventDisplayed("Test Event"), "Event not found on the calendar");
            test.pass("Event found on the calendar");
        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
