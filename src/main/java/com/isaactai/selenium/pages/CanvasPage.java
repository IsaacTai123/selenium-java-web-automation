package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * @author tisaac
 */
public class CanvasPage extends BasePage {

    private final By calendarBtn = By.id("global_nav_calendar_link");
    private final By createNewEventBtn = By.id("create_new_event_link");
    private final By titleField = By.cssSelector("input[data-testid=\"edit-calendar-event-form-title\"]");
    private final By dateField = By.cssSelector("input[data-testid=\"edit-calendar-event-form-date\"]");
    private final By startTimeField = By.cssSelector("input[data-testid=\"event-form-start-time\"]");
    private final By endTimeField = By.cssSelector("input[data-testid=\"event-form-end-time\"]");
    private final By frequencyField = By.cssSelector("input[data-testid=\"frequency-picker\"]");
    private final By locationField = By.cssSelector("input[data-testid=\"edit-calendar-event-form-location\"]");
    private final By submiteBtn = By.id("edit-calendar-event-submit-button");

    public CanvasPage(WebDriver driver) {
        super(driver);
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
        click(submiteBtn);
    }

    /**
     * Check if an event with a specific title is shown on the calendar.
     *
     * @param eventTitle The expected title text (e.g., "Selenium Assignment")
     * @return true if event is found, false otherwise
     */
    public boolean isEventDisplayed(String eventTitle) {
        List<WebElement> events = driver.findElements(By.cssSelector(".fc-title"));
        for (WebElement e : events) {
            if (e.getText().contains(eventTitle)) {
                logger.debug("Found event: {}", eventTitle);
                return true;
            }
        }
        logger.warn("Event not found: {}", eventTitle);
        return false;
    }
}
