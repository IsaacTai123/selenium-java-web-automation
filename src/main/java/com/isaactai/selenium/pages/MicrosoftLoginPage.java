package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tisaac
 */
public class MicrosoftLoginPage extends BasePage {

    // Locators for login elements
    // Microsoft Login Page
    private final By emailField = By.id("i0116");
    private final By nextButton = By.id("idSIButton9"); // "Next" button after email entry

    // Password Entry
    private final By passwordField = By.id("i0118");
    private final By signInButton = By.id("idSIButton9"); // "Sign In" button

    // Duo Security
    private final By duoAuthFrame = By.tagName("iframe"); // If Duo uses an iframe
    private final By duoApproveButton = By.id("trust-browser-button"); // By.xpath("//button[contains(text(),'Yes, this is my device')]"); // Duo approval button

    // Stay Signed In
    private final By staySignedInYesButton = By.id("idSIButton9"); // "Yes" button to stay signed in

    // Student Hub image
    private final By studentHubImage = By.cssSelector("img.header_logo__nXlyd");

    public MicrosoftLoginPage(WebDriver driver) {
        super(driver); // Call the constructor of the BasePage class
    }

    /*
    * Log in using provided credentials
    * @param username The user's NEU username
    * @param password The user's password
     */
    public void login(String username, String pwd) {
        enterText(emailField, username);
        click(nextButton);
        enterText(passwordField, pwd);
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
