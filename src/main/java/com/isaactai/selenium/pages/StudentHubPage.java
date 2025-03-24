package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author tisaac
 */
public class StudentHubPage extends BasePage {

    private final By resourcesTab = By.cssSelector("[data-testid='link-resources']");
    private final By academicClassRegistration = By.id("resource-tab-Academics,_Classes_&_Registration");
    private final By myTranscriptBtn = By.cssSelector("a[data-gtm-resources-link=\"My Transcript\"]");

    public StudentHubPage(WebDriver driver) {
        super(driver);
    }

    public void openMyTranscript() {

        click(resourcesTab);
        click(academicClassRegistration);
        scrollToElement(myTranscriptBtn);
        click(myTranscriptBtn);
        switchToNewWindow();
    }
}
