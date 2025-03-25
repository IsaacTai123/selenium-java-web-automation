package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author tisaac
 */
public class AcademicCalendarPage extends BasePage {

    private By academicCalBtn = null;
    private By iframe = null;
    private By underGradCheck = null;
    private By iframe2 = null;
    private By calendarBtn = null;

    public AcademicCalendarPage(WebDriver driver) {
        super(driver);
        loadLocator();
    }

    public void openAcademicCalendar() {
        click(academicCalBtn);
        deselectUGCheckbox();

        switchToIframe(iframe2);
        scrollToElement(calendarBtn);
    }

    public void deselectUGCheckbox() {
        switchToIframe(iframe);
        WebElement checkbox = waitUntilVisible(underGradCheck);

        if (checkbox.isSelected()) {
            clickByElement(checkbox);
            logger.debug("UG checkbox deselected");
        }

        switchToDefaultContent();
    }

    public boolean isAddToCalendarBtnVisible() {
        try {
            return waitUntilVisible(calendarBtn).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void loadLocator() {
        // Load locators from the Excel file
        String excelShareSheet = "AcademicCalendarTest";
        academicCalBtn = By.cssSelector(ExcelUtil.getCellValue(excelShareSheet, "academicCalBtn", "LocatorValue"));
        iframe = By.id(ExcelUtil.getCellValue(excelShareSheet, "iframe", "LocatorValue"));
        underGradCheck = By.id(ExcelUtil.getCellValue(excelShareSheet, "underGradCheck", "LocatorValue"));
        iframe2 = By.id(ExcelUtil.getCellValue(excelShareSheet, "iframe2", "LocatorValue"));
        calendarBtn = By.id(ExcelUtil.getCellValue(excelShareSheet, "calendarBtn", "LocatorValue"));
    }
}
