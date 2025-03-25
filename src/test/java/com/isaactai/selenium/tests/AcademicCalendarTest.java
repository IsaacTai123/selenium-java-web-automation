package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.AcademicCalendarPage;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.pages.StudentHubPage;
import com.isaactai.selenium.utils.AssertUtil;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class AcademicCalendarTest extends BaseTest {

    private final By hubLoginButton = By.cssSelector("#menu-item-menu-main-desktop-2483 a");

    // Test method to verify the academic calendar
    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Academic Calendar Test triggered ===");
        test = extent.createTest("Academic Calendar Scenario");

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get("https://about.me.northeastern.edu/home/"); // Open the target URL
        microsoftLoginPage.click(hubLoginButton);
        microsoftLoginPage.login("tai.hs@northeastern.edu", "fibtap-3xobho-zAxbyf$0");
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

}
