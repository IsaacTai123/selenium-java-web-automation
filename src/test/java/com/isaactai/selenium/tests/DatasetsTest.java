package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.NeuLibraryBostonPage;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tisaac
 */
public class DatasetsTest extends BaseTest {

    private final String downloadDir = System.getProperty("user.dir") + "/downloads";

    @Override
    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/tisaac/chrome/mac_arm-122.0.6261.128/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
        options.addArguments("--start-maximized");

        // Set Chrome default auto-download folder + no pop-up
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir);
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);

        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    @BeforeMethod
    public void setup() {
        logger.debug("=== Set up Datasets Test triggered ===");
        test = extent.createTest("Datasets Scenario");

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        File dir = new File(downloadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Initialize the WebDriver instance here: ChromeDriver
        driver.get("https://onesearch.library.northeastern.edu/discovery/search?vid=01NEU_INST:NU&lang=en");


    }

    // Example test method
    @Test
    public void testDatasetFunctionality() {
        logger.debug("=== Test Dataset Functionality triggered ===");

        NeuLibraryBostonPage neuLibraryBostonPage = new NeuLibraryBostonPage(driver);
        neuLibraryBostonPage.navigateToDigitalRepositoryService();
        neuLibraryBostonPage.downloadZipFile();

        File downloadedFile = waitForFileDownloaded(neuLibraryBostonPage.getFileName(), 40);

        test.info("Expected: File downloaded to " + downloadDir);
        test.info("Actual: " + (downloadedFile != null ? downloadedFile.getAbsolutePath() : "none"));

        if (downloadedFile != null && downloadedFile.exists()) {
            test.pass("File downloaded successfully: " + downloadedFile.getName());
        } else {
            test.fail("File was not downloaded.");
            Assert.fail("File download failed.");
        }
    }

    private File waitForFileDownloaded(String fileName, int timeoutSeconds) {
        File file = new File(downloadDir + "/" + fileName);
        logger.debug("Waiting for file: " + file.getAbsolutePath());
        int waited = 0;

        while (waited < timeoutSeconds) {
            if (file.exists()) {
                return file;
            }
            try {
                Thread.sleep(1000);
                waited++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }
}
