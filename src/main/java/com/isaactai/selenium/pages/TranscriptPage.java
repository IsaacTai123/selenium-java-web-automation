package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tisaac
 */
public class TranscriptPage extends BasePage {

    private final By transcriptLevelDropdown = By.id("levl_id");
    private final By transcriptTypeDropdown = By.id("type_id");
    private final By submitBtn = By.cssSelector("input[type='submit'][value='Submit']");
    private final By transcriptContainer = By.cssSelector("table.datadisplaytable");

    public TranscriptPage(WebDriver driver) {
        super(driver);
        logger.debug("transcript page initialized");
    }

    // Select Transcript Level as “Graduate” and Transcript Type as “Audit Transcript”.
    public void selectTranscriptOptions(String level, String type) {
        selectFromDropdown(transcriptLevelDropdown, level);
        selectFromDropdown(transcriptTypeDropdown, type);
        click(submitBtn);
    }

    public void openTranscriptPreview() {
        // wait for transcript to show
        logger.debug("Waiting for transcript to show");
        waitUntilVisible(transcriptContainer);
    }
}
