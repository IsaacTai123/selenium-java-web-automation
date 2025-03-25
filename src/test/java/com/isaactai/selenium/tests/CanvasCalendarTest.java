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

        logger.debug("=== Test Canvas Calendar triggered ===");

        String[][] events = {
            {"Test Event", "Wed, Mar 26, 2025", "10:00 AM", "11:30 AM", "Does not repeat", "Northeastern Curry Student Center"},
            {"Test Event 2", "Fri, Apr 4, 2025", "1:30 PM", "4:30 PM", "Does not repeat", "Egan Engineering and Science Center"}
        };

        for (String[] event : events) {
            String title = event[0];
            String date = event[1];
            String startTime = event[2];
            String endTime = event[3];
            String frequency = event[4];
            String location = event[5];

            try {
                canvasPage.clickCalendarBtn();
                canvasPage.clickCreateNewEventBtn();
                canvasPage.createNewEvent(title, date, startTime, endTime, frequency, location);

                test.info("Expected: Event \"" + title + "\" appears on the calendar");
                boolean isDisplayed = canvasPage.isEventDisplayed(title);
                test.info("Actual: " + (isDisplayed
                        ? "Event \"" + title + "\" was found on the calendar"
                        : "Event \"" + title + "\" was NOT found on the calendar"));

                Assert.assertTrue(isDisplayed, "Event not found on calendar: " + title);
                test.pass("Event \"" + title + "\" verification passed");

            } catch (Exception e) {
                test.fail("Event \"" + event[0] + "\" failed due to exception: " + e.getMessage());
                Assert.fail("Test failed for event \"" + event[0] + "\": " + e.getMessage());
            }
        }
    }
}
