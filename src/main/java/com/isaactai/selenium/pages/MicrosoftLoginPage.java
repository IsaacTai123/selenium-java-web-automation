package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author tisaac
 */
public class MicrosoftLoginPage extends BasePage {

    String microsoftUsernameLocator = ExcelUtil.getCellValue("TranscriptTest", "microsoftUsername", "LocatorValue");
    String microsoftPasswordLocator = ExcelUtil.getCellValue("TranscriptTest", "microsoftPassword", "LocatorValue");
    String nextButtonLocator = ExcelUtil.getCellValue("TranscriptTest", "nextButton", "LocatorValue");
    String duoApproveLocator = ExcelUtil.getCellValue("TranscriptTest", "duoApproveButton", "LocatorValue");
    String studentHubImageLocator = ExcelUtil.getCellValue("TranscriptTest", "studentHubImage", "LocatorValue");

    // Locators for login elements
    // Microsoft Login Page
    private final By microsoftUsername = By.id(microsoftUsernameLocator);
    private final By nextButton = By.id(nextButtonLocator); // "Next" button after email entry

    // Password Entry
    private final By microsoftPwd = By.id(microsoftPasswordLocator);
    private final By signInButton = By.id(nextButtonLocator); // "Sign In" button

    // Duo Security
    private final By duoApproveButton = By.id(duoApproveLocator); // By.xpath("//button[contains(text(),'Yes, this is my device')]"); // Duo approval button

    // Stay Signed In
    private final By staySignedInYesButton = By.id(nextButtonLocator); // "Yes" button to stay signed in

    // Student Hub image
    private final By studentHubImage = By.cssSelector(studentHubImageLocator);

    public MicrosoftLoginPage(WebDriver driver) {
        super(driver); // Call the constructor of the BasePage class
    }

    /*
    * Log in using provided credentials
    * @param username The user's NEU username
    * @param password The user's password
     */
    public void login(String username, String pwd) {
        enterText(microsoftUsername, username);
        click(nextButton);
        enterText(microsoftPwd, pwd);
        click(signInButton);

        // Handle Duo authentication (automatic step)
        logger.debug("Start handling Duo authentication...");
        click(duoApproveButton);
        logger.debug("Duo authentication handled.");

        // Handle "Stay signed in?" prompt
        if (isElementPresent(staySignedInYesButton)) {
            click(staySignedInYesButton);
        }
    }

    /*
    * Check if the user has successfully logged in
    * @return true if login is successful, false otherwise
     */
    public boolean isLoginSuccessful() {
        return isElementPresent(studentHubImage);
    }
}
