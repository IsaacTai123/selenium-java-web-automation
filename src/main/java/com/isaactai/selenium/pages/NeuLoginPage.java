package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author tisaac
 */
public class NeuLoginPage extends BasePage {

    private By usernameField = null;
    private By pwdField = null;
    private By loginBtn = null;

    public NeuLoginPage(WebDriver driver) {
        super(driver);
        loadLocator();
    }

    public void login(String username, String pwd) {
        enterText(usernameField, username);
        enterText(pwdField, pwd);
        click(loginBtn);
        logger.debug("Successfully login to NEU page");
    }

    public void loadLocator() {
        // Load locators from the Excel file
        String excelSheetName = "ShareData";
        usernameField = By.id(ExcelUtil.getCellValue(excelSheetName, "neuUsername", "LocatorValue"));
        pwdField = By.id(ExcelUtil.getCellValue(excelSheetName, "neuPassword", "LocatorValue"));
        loginBtn = By.name(ExcelUtil.getCellValue(excelSheetName, "neuLoginBtn", "LocatorValue"));
    }
}
