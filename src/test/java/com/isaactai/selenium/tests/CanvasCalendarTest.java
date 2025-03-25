package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.CanvasPage;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.utils.ExcelUtil;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author tisaac
 */
public class CanvasCalendarTest extends BaseTest {

    private CanvasPage canvasPage;
    private By canvasLogin = null;
    private String testName;
    private String microsoftUsername;
    private String microsoftPassword;
    private String canvasUrl;
    private Map<String, String> testData;
    private Map<String, String> testData2;


    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Canvas Calendar Test triggered ===");
        loadCanvasTestData();
        test = extent.createTest(testName);

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);


        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get(canvasUrl);
        microsoftLoginPage.click(canvasLogin);
        microsoftLoginPage.login(microsoftUsername, microsoftPassword);

        canvasPage = new CanvasPage(driver);
    }

    @Test
    public void testCanvasCalendar() {

        logger.debug("=== Test Canvas Calendar triggered ===");

        String[][] events = {
            {testData.get("Title"), testData.get("Date"), testData.get("StartTime"), testData.get("EndTime"), testData.get("Frequency"), testData.get("Location")},
            {testData2.get("Title"), testData2.get("Date"), testData2.get("StartTime"), testData2.get("EndTime"), testData2.get("Frequency"), testData2.get("Location")}
        };

        // debuging
        for (String[] event : events) {
            logger.debug("=== Event ===");
            for (String field : event) {
                logger.debug(field);
            }
        }

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

    public void loadCanvasTestData() {
        // Load test data from Excel or any other source
        // For example:
        String excelSheetName = "CanvasCalendarTest";
        canvasLogin = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "canvasLogin", "LocatorValue"));
        testName = ExcelUtil.getCellValue(excelSheetName, "testName", "TestData");
        canvasUrl = ExcelUtil.getCellValue(excelSheetName, "canvasUrl", "TestData");

        String shareSheetName = "ShareData";
        microsoftUsername = ExcelUtil.getCellValue(shareSheetName, "microsoftUsername", "TestData");
        microsoftPassword = ExcelUtil.getCellValue(shareSheetName, "microsoftPassword", "TestData");

        String eventSheet = "CanvasEvents";
        testData = ExcelUtil.getRowData(eventSheet, "Test Event 1");
        testData2 = ExcelUtil.getRowData(eventSheet, "Test Event 2");
    }
}
