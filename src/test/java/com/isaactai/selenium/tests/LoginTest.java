package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.MicrosoftLoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class LoginTest extends BaseTest {

    private MicrosoftLoginPage microsoftLoginPage;

    // Click Hub Login
    private final By hubLoginButton = By.cssSelector("#menu-item-menu-main-desktop-2483 a"); // or By.xpath("//a[@title='Hub Login']");

    @BeforeClass
    public void setUpPage() {
        microsoftLoginPage = new MicrosoftLoginPage(driver);
        driver.get("https://about.me.northeastern.edu/home/"); // Open the target URL
        microsoftLoginPage.click(hubLoginButton);
    }

    @Test
    public void testLogin() {
        microsoftLoginPage.login("tai.hs@northeastern.edu", "");
        Assert.assertTrue(microsoftLoginPage.isLoginSuccessful(), "Login failed or element not found");
    }

}
