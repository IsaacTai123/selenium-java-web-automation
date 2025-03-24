package com.isaactai.selenium.base;

import com.isaactai.selenium.pages.MicrosoftLoginPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Set;

/**
 * @author tisaac
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(MicrosoftLoginPage.class);
    protected WebDriver driver; // WebDriver instance used for all tests
    protected Set<Cookie> savedCookies; // Cookies to be saved for later use

    // BeforeClass: Runs before each test class execution. Initializes the WebDriver.
    @BeforeMethod
    public void setUp() {
        logger.debug("=== Initialize Web Driver ===");
        // Initialize the WebDriver instance here: ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/Users/tisaac/chromedriver/mac_arm-122.0.6261.128/chromedriver-mac-arm64/chromedriver");

        // Configure Chrome Driver options
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/tisaac/chrome/mac_arm-122.0.6261.128/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
        options.addArguments("--start-maximized"); // Maximize browser window
        options.addArguments("--disable-notifications"); // Disable pop-up notifications

        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        logger.debug("=== tearDown() triggered ===");
        // Close the browser after each test class execution
        if (driver != null) {
            driver.quit();
        }
    }
}
