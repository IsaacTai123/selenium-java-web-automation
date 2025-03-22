package com.isaactai.selenium.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author tisaac
 */
public class BaseTest {
    protected WebDriver driver; // WebDriver instance used for all tests

    // BeforeClass: Runs before each test class execution. Initializes the WebDriver.
    @BeforeClass
    public void setUp() {
        // Initialize the WebDriver instance here: ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/Users/tisaac/chromedriver/mac_arm-122.0.6261.128/chromedriver-mac-arm64/chromedriver");

        // Configure Chrome Driver options
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/tisaac/chrome/mac_arm-122.0.6261.128/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
        options.addArguments("--start-maximized"); // Maximize browser window
        options.addArguments("--disable-notifications"); // Disable pop-up notifications

        driver = new ChromeDriver(options);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after each test class execution
        if (driver != null) {
            driver.quit();
        }
    }
}
