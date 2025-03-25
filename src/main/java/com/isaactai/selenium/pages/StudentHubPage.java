package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author tisaac
 */
public class StudentHubPage extends BasePage {

    private By resourcesTab = null;
    private By academicClassRegistration = null;
    private By myTranscriptBtn = null;
    private By AcademicCalendar = null;

    public StudentHubPage(WebDriver driver) {
        super(driver);
        loadLocator();
    }

    public void openMyTranscript() {
        click(resourcesTab);
        click(academicClassRegistration);
        scrollToElement(myTranscriptBtn);
        click(myTranscriptBtn);
        switchToNewWindow();
    }

    public void openAcademicCalendar() {
        click(resourcesTab);
        click(academicClassRegistration);
        scrollToElement(AcademicCalendar);
        click(AcademicCalendar);
        switchToNewWindow();
    }

    public void loadLocator() {
        // Load locators from the Excel file
        String excelShareSheet = "ShareData";
        resourcesTab = By.cssSelector(ExcelUtil.getCellValue(excelShareSheet, "resourcesTab", "LocatorValue"));
        academicClassRegistration = By.id(ExcelUtil.getCellValue(excelShareSheet, "academicClassRegistration", "LocatorValue"));

        String transcriptSheet = "TranscriptTest";
        myTranscriptBtn = By.cssSelector(ExcelUtil.getCellValue(transcriptSheet, "myTranscriptBtn", "LocatorValue"));

        String AcademicCalendarSheet = "AcademicCalendarTest";
        AcademicCalendar = By.cssSelector(ExcelUtil.getCellValue(AcademicCalendarSheet, "AcademicCalendar", "LocatorValue"));

    }
}
