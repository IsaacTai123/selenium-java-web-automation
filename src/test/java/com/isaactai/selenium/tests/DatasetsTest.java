package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.NeuLibraryBostonPage;
import com.isaactai.selenium.utils.ExcelUtil;
import com.isaactai.selenium.utils.FileUtil;
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

    private String downloadDir;
    private String testName;
    private String libraryUrl;
    private String waitForDownloadSec;


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

    @Override
    @BeforeMethod
    public void setUp() {
        loadDatasetsTestData();

        driver = createDriver(getChromeOptions());

        logger.debug("=== Set up Datasets Test triggered ===");
        test = extent.createTest(testName);

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        File dir = new File(downloadDir);
        FileUtil.ensureParentDirExists(dir);

        // Initialize the WebDriver instance here: ChromeDriver
        driver.get(libraryUrl);
    }

    // Example test method
    @Test
    public void testDatasetFunctionality() {
        logger.debug("=== Test Dataset Functionality triggered ===");

        NeuLibraryBostonPage neuLibraryBostonPage = new NeuLibraryBostonPage(driver);
        neuLibraryBostonPage.navigateToDigitalRepositoryService();
        neuLibraryBostonPage.downloadZipFile();

        int timeoutSec = Integer.parseInt(waitForDownloadSec);
        File downloadedFile = waitForFileDownloaded(neuLibraryBostonPage.getFileName(), timeoutSec);

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

    public void loadDatasetsTestData() {
        // Load test data from Excel or any other source
        String excelSheetName = "DatasetsTest";
        testName = ExcelUtil.getCellValue(excelSheetName, "testName", "TestData");
        downloadDir = System.getProperty("user.dir") + ExcelUtil.getCellValue(excelSheetName, "downloadPath", "TestData");
        libraryUrl = ExcelUtil.getCellValue(excelSheetName, "libraryUrl", "TestData");
        waitForDownloadSec = ExcelUtil.getCellValue(excelSheetName, "waitForDownloadSec", "TestData");
    }
}
