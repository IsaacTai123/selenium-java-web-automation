package com.isaactai.selenium.tests;

import com.isaactai.selenium.base.BaseTest;
import com.isaactai.selenium.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeClass
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        driver.get("https://about.me.northeastern.edu/home/"); // Open the target URL
    }

    @Test
    public void testLogin() {
        loginPage.login("tai.hs@northeastern.edu", "");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed or element not found");
    }

}
