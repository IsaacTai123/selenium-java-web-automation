package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.pages.NeuLoginPage;
import com.isaactai.selenium.pages.StudentHubPage;
import com.isaactai.selenium.pages.TranscriptPage;
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
    private final By hubLoginButton = By.cssSelector("#menu-item-menu-main-desktop-2483 a");

    @BeforeMethod
    public void setUpPage() {
        logger.debug("=== Set up Transcript Test triggered ===");
        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get("https://about.me.northeastern.edu/home/"); // Open the target URL
        microsoftLoginPage.click(hubLoginButton);
        microsoftLoginPage.login("tai.hs@northeastern.edu", "fibtap-3xobho-zAxbyf$0");

        StudentHubPage studentHubPage = new StudentHubPage(driver);
        studentHubPage.openMyTranscript();

        NeuLoginPage neuLoginPage = new NeuLoginPage(driver);
        neuLoginPage.login("tai.hs", "fibtap-3xobho-zAxbyf$0");

        transcriptPage = new TranscriptPage(driver);
    }

    @Test
    public void testTranscript() throws Exception {
        transcriptPage.selectTranscriptOptions("Graduate", "Audit Transcript");
        transcriptPage.waitForTranscriptShow();

        String pdfPath = System.getProperty("user.dir") + "/downloads/academic transcript.pdf";
        transcriptPage.saveCurrentPageAsPDF(pdfPath);

        File file = new File(pdfPath);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        Assert.assertTrue(text.contains("Curriculum Information"), "PDF should contain 'Curriculum Information'");
    }
}
