package com.isaactai.selenium.utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * @author tisaac
 */
public class AssertUtil {
    public static void verifyTrue(WebDriver driver, ExtentTest test,
                                  boolean condition,
                                  String expectedMsg, String actualMsg,
                                  String passMsg, String failMsg) {
        test.info("Expected: " + expectedMsg);
        test.info("Actual: " + actualMsg);

        if (condition) {
            test.pass(passMsg);
        } else {
            ScreenshotUtil.takeScreenshot(driver, "assert_fail");
            test.fail(failMsg);
            Assert.fail(failMsg);
        }
    }
}
