package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.AcademicCalendarPage;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.pages.StudentHubPage;
import com.isaactai.selenium.utils.AssertUtil;
import com.isaactai.selenium.utils.ExcelUtil;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class AcademicCalendarTest extends BaseTest {

    private String hubLoginLocator;
    String microsoftUsername;
    String microsoftPassword;
    String studentHubUrl;
    String testName;

    // Test method to verify the academic calendar
    @BeforeMethod
    public void setup() {
        loadAcademicCalendarTestData();
        logger.debug("=== Set up Academic Calendar Test triggered ===");
        test = extent.createTest(testName);

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get(studentHubUrl); // Open the target URL
        By hubLoginBtn = By.cssSelector(hubLoginLocator);
        microsoftLoginPage.click(hubLoginBtn);
        microsoftLoginPage.login(microsoftUsername, microsoftPassword);
    }

    @Test
    public void testAcademicCalendar() {
        logger.debug("=== testAcademicCalendar() triggered ===");

        StudentHubPage studentHubPage = new StudentHubPage(driver);
        studentHubPage.openAcademicCalendar();

        AcademicCalendarPage calendarPage = new AcademicCalendarPage(driver);
        calendarPage.openAcademicCalendar();

        AssertUtil.verifyTrue(driver, test,
                calendarPage.isAddToCalendarBtnVisible(),
                "Button should appear",
                "Button is not visible",
                "Add to My Calendar' button is visible",
                "Add to My Calendar' button is not visible"
        );

    }

    public void loadAcademicCalendarTestData() {
        // Load test data from Excel
        String excelShareSheet = "ShareData";

        microsoftUsername = ExcelUtil.getCellValue(excelShareSheet, "microsoftUsername", "TestData");
        microsoftPassword = ExcelUtil.getCellValue(excelShareSheet, "microsoftPassword", "TestData");

        String excelSheetName = "AcademicCalendarTest";
        studentHubUrl = ExcelUtil.getCellValue(excelSheetName, "studentHubUrl", "TestData");
        hubLoginLocator = ExcelUtil.getCellValue(excelSheetName, "hubLoginButton", "LocatorValue");
        testName = ExcelUtil.getCellValue(excelSheetName, "testName", "TestData");

    }

}
