package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
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

    private By reserveStudyRoomBtn = null;
    private By bostonLibrary = null;
    private By bookRoomBtn = null;
    private By seatStyle = null; // select "Individual Study"
    private By capacity = null; // select " Space For 1-4 people "
    private By nextAvailableBtn = null;
    private By availTime = null;
    private By submitBtn = null;
    private By confirmBtn = null;
    private By reservationTitleField = null; // input field
    private By bookingBtn = null;
    private By verify = null;
    private String seatStyleOption;
    private String capacityOption;
    private String time;
    private String reservationTitleOption;



    public SnellLibraryPage(WebDriver driver) {
        super(driver);
        loadLocator();
    }

    public void bookStudyRoom() {
        click(reserveStudyRoomBtn);
        click(bostonLibrary);
        click(bookRoomBtn);
        selectFromDropdown(seatStyle, seatStyleOption);
        selectFromDropdown(capacity, capacityOption);
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
            scrollToTime(time);
            scrollToBottomAndClick(targetSlot);
            waitForSeconds(1);
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
        enterText(reservationTitleField, reservationTitleOption);
        click(bookingBtn);
    }

    public boolean isBookingConfirmed() {
        WebElement h1 = driver.findElement(verify);
        return h1.getText().contains("Booking Confirmed");
    }

    public void loadLocator() {
        // Load locators from Excel or any other source
        // For example:
        String excelSheetName = "ReserveStudyRoomTest";
        reserveStudyRoomBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "reserveStudyRoomBtn", "LocatorValue"));
        bostonLibrary = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "bostonLibrary", "LocatorValue"));
        bookRoomBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "bookRoomBtn", "LocatorValue"));
        seatStyle = By.id(ExcelUtil.getCellValue(excelSheetName, "seatStyle", "LocatorValue"));
        capacity = By.id(ExcelUtil.getCellValue(excelSheetName, "capacity", "LocatorValue"));
        nextAvailableBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "nextAvailableBtn", "LocatorValue"));
        availTime = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "availTime", "LocatorValue"));
        submitBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "submitBtn", "LocatorValue"));
        confirmBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "confirmBtn", "LocatorValue"));
        reservationTitleField = By.id(ExcelUtil.getCellValue(excelSheetName, "reservationTitleField", "LocatorValue"));
        bookingBtn = By.id(ExcelUtil.getCellValue(excelSheetName, "bookingBtn", "LocatorValue"));
        verify = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "verify", "LocatorValue"));
        seatStyleOption = ExcelUtil.getCellValue(excelSheetName, "seatStyle", "TestData");
        capacityOption = ExcelUtil.getCellValue(excelSheetName, "capacity", "TestData");
        time = ExcelUtil.getCellValue(excelSheetName, "time", "TestData");
        reservationTitleOption = ExcelUtil.getCellValue(excelSheetName, "reservationTitleField", "TestData");
    }
}
