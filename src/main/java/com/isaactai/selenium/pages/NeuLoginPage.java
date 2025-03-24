package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tisaac
 */
public class NeuLoginPage extends BasePage {

    private final By usernameField = By.id("username");
    private final By pwdField = By.id("password");
    private final By loginBtn = By.name("_eventId_proceed");

    public NeuLoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String pwd) {
        enterText(usernameField, username);
        enterText(pwdField, pwd);
        click(loginBtn);
        logger.debug("Successfully login to NEU page");
    }

}
