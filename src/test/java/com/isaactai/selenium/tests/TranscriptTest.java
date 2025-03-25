package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.pages.NeuLoginPage;
import com.isaactai.selenium.pages.StudentHubPage;
import com.isaactai.selenium.pages.TranscriptPage;
import com.isaactai.selenium.utils.ExcelUtil;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author tisaac
 */
public class TranscriptTest extends BaseTest {

    private TranscriptPage transcriptPage;
    String hubLoginLocator;
    String microsoftUsername;
    String microsoftPassword;
    String studentHubUrl;
    String neuUsername;
    String neuPassword;
    String transcriptLevel;
    String transcriptType;
    String downloadPath;
    String testName;



    @BeforeMethod
    public void setUpPage() {
        logger.debug("=== Set up Transcript Test triggered ===");
        loadTranscriptTestData(); // Load test data from Excel
        test = extent.createTest(testName);

        // Set up the scenario name for screenshots and clear previous screenshots
        String scenarioName = this.getClass().getSimpleName();
        ScreenshotUtil.setScenarioName(scenarioName);
        ScreenshotUtil.clearScreenshotFolder("screenshots/" + scenarioName);

        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get(studentHubUrl); // Open the target URL

        By hubLoginButton =  By.cssSelector(hubLoginLocator);
        microsoftLoginPage.click(hubLoginButton);
        microsoftLoginPage.login(microsoftUsername, microsoftPassword);

        StudentHubPage studentHubPage = new StudentHubPage(driver);
        studentHubPage.openMyTranscript();

        NeuLoginPage neuLoginPage = new NeuLoginPage(driver);
        neuLoginPage.login(neuUsername, neuPassword);

        transcriptPage = new TranscriptPage(driver);
    }

    @Test
    public void testTranscript() {
        try {
            transcriptPage.selectTranscriptOptions(transcriptLevel, transcriptType);
            transcriptPage.waitForTranscriptShow();

            String pdfPath = System.getProperty("user.dir") + downloadPath;
            transcriptPage.saveCurrentPageAsPDF(pdfPath);

            File file = new File(pdfPath);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            // Add Expected vs Actual to the report
            String expected = "Curriculum Information";
            boolean contentMatched = text.contains(expected);

            test.info("Expected: The transcript PDF should contain the phrase: \"" + expected + "\"");
            test.info("Actual: " + (contentMatched
                    ? "The phrase \"" + expected + "\" was found in the transcript."
                    : "The phrase \"" + expected + "\" was NOT found in the transcript."));

            Assert.assertTrue(text.contains("Curriculum Information"), "PDF should contain 'Curriculum Information'");
            test.pass("Transcript PDF verification passed.\n");
        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void loadTranscriptTestData() {
        hubLoginLocator = ExcelUtil.getCellValue("TranscriptTest", "hubLoginButton", "LocatorValue");
        microsoftUsername = ExcelUtil.getCellValue("TranscriptTest", "microsoftUsername", "TestData");
        microsoftPassword = ExcelUtil.getCellValue("TranscriptTest", "microsoftPassword", "TestData");
        studentHubUrl = ExcelUtil.getCellValue("TranscriptTest", "studentHubUrl", "TestData");
        neuUsername = ExcelUtil.getCellValue("TranscriptTest", "neuUsername", "TestData");
        neuPassword = ExcelUtil.getCellValue("TranscriptTest", "neuPassword", "TestData");
        transcriptLevel = ExcelUtil.getCellValue("TranscriptTest", "transcriptLevel", "TestData");
        transcriptType = ExcelUtil.getCellValue("TranscriptTest", "transcriptType", "TestData");
        downloadPath = ExcelUtil.getCellValue("TranscriptTest", "downloadPath", "TestData");
        testName = ExcelUtil.getCellValue("TranscriptTest", "testName", "TestData");
    }
}
