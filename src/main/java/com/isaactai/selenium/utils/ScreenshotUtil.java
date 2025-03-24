package com.isaactai.selenium.utils;

import com.isaactai.selenium.pages.MicrosoftLoginPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author tisaac
 * Utility class for taking screenshots before and after test actions.
 * Screenshots will be saved in folders based on the test class name.
 *
 */
public class ScreenshotUtil {

    protected static final Logger logger = LoggerFactory.getLogger(MicrosoftLoginPage.class);
    /**
     * Takes a screenshot and saves it to the appropriate scenario folder.
     *
     * @param driver The active WebDriver instance
     * @param label A label to describe the context of the screenshot (e.g., before_click_login)
     */
    public static void takeScreenshot(WebDriver driver, String label) {
        try {
            // Use the calling test class name as the scenario folder
            String scenario = getScenarioName();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String folderPath = "screenshots/" + scenario;
            String fileName = folderPath + "/" + label + "_" + timestamp + ".png";

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fileName);
            dest.getParentFile().mkdirs(); // Create folder if it doesn't exist
            Files.copy(src.toPath(), dest.toPath());

            logger.info("📸 Screenshot saved: " + fileName);
        } catch (IOException e) {
            logger.error("❌ Screenshot failed: " + e.getMessage());
        }
    }

    /**
     * Extracts the test class name from the call stack to name the scenario folder.
     *
     * @return The test class name (e.g., TranscriptTest)
     */
    private static String getScenarioName() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains("com.isaactai.selenium.tests")) {
                return element.getClassName().substring(element.getClassName().lastIndexOf('.') + 1);
            }
        }
        return "UnknownScenario";
    }
}
