package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.pages.NeuLoginPage;
import com.isaactai.selenium.pages.StudentHubPage;
import com.isaactai.selenium.pages.TranscriptPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author tisaac
 */
public class TranscriptTest extends BaseTest {

    private TranscriptPage transcriptPage;
    private final By hubLoginButton = By.cssSelector("#menu-item-menu-main-desktop-2483 a");

    @BeforeClass
    public void setUpPage() {
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
    public void testTranscript() {
        transcriptPage.selectTranscriptOptions("Graduate", "Audit Transcript");
        transcriptPage.openTranscriptPreview();
        transcriptPage.saveCurrentPageAsPDF(System.getProperty("user.dir") + "/downloads/transcripts/transcript.pdf");

        // Check if download success
        File file = new File(System.getProperty("user.dir") + "/downloads/transcripts/transcript.pdf");
        Assert.assertTrue(file.exists(), "Transcript PDF should exist");
    }
}
