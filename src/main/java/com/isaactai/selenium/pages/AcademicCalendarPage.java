package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author tisaac
 */
public class AcademicCalendarPage extends BasePage {

    private final By academicCalBtn = By.cssSelector("a[href=\"https://registrar.northeastern.edu/article/academic-calendar/\"]");
    private final By iframe = By.id("trumba.spud.7.iframe");
    private final By underGradCheck = By.id("mixItem0");
    private final By iframe2 = By.id("trumba.spud.2.iframe");
    private final By calendarBtn = By.id("ctl04_ctl103_ctl00_buttonAtmc");

    public AcademicCalendarPage(WebDriver driver) {
        super(driver);
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
}
