package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author tisaac
 */
public class SnellLibraryPage extends BasePage {

    private final By reserveStudyRoomBtn = By.cssSelector("a[href='/library-locations/library-rooms-spaces/']");
    private final By bostonLibrary = By.cssSelector("a.pt-cv-href-thumbnail[href=\"https://library.northeastern.edu/ideas/rooms-spaces/\"]");
    private final By bookRoomBtn = By.cssSelector("a.simple-button[href=\"https://northeastern.libcal.com/reserve/spaces/studyspace\"]");
    private final By seatStyleDropDown = By.id("gid"); // select "Individual Study"
    private final By capacityDropDown = By.id("capacity"); // select " Space For 1-4 people "
    private final By nextAvailableBtn = By.cssSelector("button[aria-label='Next Available']");
    private final By availTime = By.cssSelector("a.s-lc-eq-avail");
    private final By submitBtn = By.id("submit_times");
    private final By confirmBtn = By.id("terms_accept");
    private final By reservationTitleField = By.id("nick"); // input field
    private final By bookingBtn = By.id("btn-form-submit");
    private final By verify = By.cssSelector("h1.s-lc-eq-success-title");



    public SnellLibraryPage(WebDriver driver) {
        super(driver);
    }

    public void bookStudyRoom() {
        click(reserveStudyRoomBtn);
        click(bostonLibrary);
        click(bookRoomBtn);
        selectFromDropdown(seatStyleDropDown, "Individual Study");
        selectFromDropdown(capacityDropDown, "Space For 1-4 people");
        clickIfPresent(nextAvailableBtn);
        clickAvailableTimeSlot(10);

        click(submitBtn);
    }

    public void clickAvailableTimeSlot(int fromEndIndex) {

        List<WebElement> slots = driver.findElements(availTime);
        int index = slots.size() - fromEndIndex;

        if (index >= 0 && index < slots.size()) {
            WebElement targetSlot = slots.get(index);

            ScreenshotUtil.takeScreenshot(driver, "before_click_dynamicSlot_" + index);
            scrollToTime("8:00am");
            scrollToBottomAndClick(targetSlot);
            ScreenshotUtil.takeScreenshot(driver, "after_click_dynamicSlot_" + index);
        } else {
            throw new RuntimeException("Not enough available slots to click at index " + fromEndIndex);
        }
    }

    public void scrollToTime(String timeLabelText) {
        try {
            WebElement timeLabel = driver.findElement(By.xpath("//span[text()='" + timeLabelText + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", timeLabel);
        } catch (NoSuchElementException e) {
            System.out.println("Time label '" + timeLabelText + "' not found.");
        }
    }

    public void confirmBooking() {
        click(confirmBtn);
        enterText(reservationTitleField, "Study Selenium Test");
        click(bookingBtn);
    }

    public boolean isBookingConfirmed() {
        WebElement h1 = driver.findElement(verify);
        return h1.getText().contains("Booking Confirmed");
    }
}
