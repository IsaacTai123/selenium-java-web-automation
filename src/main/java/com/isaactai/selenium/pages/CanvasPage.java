package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * @author tisaac
 */
public class CanvasPage extends BasePage {

    private By calendarBtn = null;
    private By createNewEventBtn = null;
    private By titleField = null;
    private By dateField = null;
    private By startTimeField = null;
    private By endTimeField = null;
    private By frequencyField = null;
    private By locationField = null;
    private By submitBtn = null;
    private By event = null;

    public CanvasPage(WebDriver driver) {
        super(driver);
        loadLocator();
    }

    public void clickCalendarBtn() {
        click(calendarBtn);
    }

    public void clickCreateNewEventBtn() {
        click(createNewEventBtn);
    }

    public void createNewEvent(String title, String date, String startTime, String endTime, String frequency, String location) {
        enterText(titleField, title);
        enterText(dateField, date);
        enterText(startTimeField, startTime);
        enterText(endTimeField, endTime);
        selectFromCustomDropdown(frequencyField, frequency);
        enterText(locationField, location);
        click(submitBtn);
    }

    /**
     * Check if an event with a specific title is shown on the calendar.
     *
     * @param eventTitle The expected title text (e.g., "Selenium Assignment")
     * @return true if event is found, false otherwise
     */
    public boolean isEventDisplayed(String eventTitle) {
        List<WebElement> events = driver.findElements(event);
        for (WebElement e : events) {
            if (e.getText().contains(eventTitle)) {
                logger.debug("Found event: {}", eventTitle);
                return true;
            }
        }
        logger.warn("Event not found: {}", eventTitle);
        return false;
    }

    public void loadLocator() {
        String excelSheetName = "CanvasCalendarTest";
        calendarBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "calendarBtn", "LocatorValue"));
        createNewEventBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "createNewEventBtn", "LocatorValue"));
        titleField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "titleField", "LocatorValue"));
//        titleField = By.cssSelector("input[data-testid=\"edit-calendar-event-form-title\"]");
        dateField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "dateField", "LocatorValue"));
        startTimeField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "startTimeField", "LocatorValue"));
        endTimeField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "endTimeField", "LocatorValue"));
        frequencyField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "frequencyField", "LocatorValue"));
        locationField = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "locationField", "LocatorValue"));
        submitBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "submitBtn", "LocatorValue"));
        event = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "event", "LocatorValue"));
    }
}
