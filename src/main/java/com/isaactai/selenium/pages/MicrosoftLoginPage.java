package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author tisaac
 */
public class MicrosoftLoginPage extends BasePage {

    // Locators for login elements
    // Microsoft Login Page
    private By microsoftUsername = null;
    private By nextButton = null; // "Next" button after email entry
    private By microsoftPwd = null;
    private By signInButton = null; // "Sign In" button
    private By duoApproveButton = null; // Duo approval button
    private By staySignedInYesButton = null; // "Yes" button to stay signed in

    // Student Hub image
    private By studentHubImage = null;

    public MicrosoftLoginPage(WebDriver driver) {
        super(driver); // Call the constructor of the BasePage class
        loadLocator(); // Load locators from the Excel file
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

    public void loadLocator() {
        // Load locators from the Excel file
        String excelSheetName = "TranscriptTest";
        microsoftUsername = By.id(ExcelUtil.getCellValue(excelSheetName, "microsoftUsername", "LocatorValue"));
        nextButton = By.id(ExcelUtil.getCellValue(excelSheetName, "nextButton", "LocatorValue"));
        microsoftPwd = By.id(ExcelUtil.getCellValue(excelSheetName, "microsoftPassword", "LocatorValue"));
        signInButton = By.id(ExcelUtil.getCellValue(excelSheetName, "nextButton", "LocatorValue"));
        duoApproveButton = By.id(ExcelUtil.getCellValue(excelSheetName, "duoApproveButton", "LocatorValue"));
        staySignedInYesButton = By.id(ExcelUtil.getCellValue(excelSheetName, "nextButton", "LocatorValue"));
        studentHubImage = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "studentHubImage", "LocatorValue"));
    }
}
