package com.isaactai.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage class provides common methods for interacting with web elements.
 * Check more details at <a href="https://www.javadoc.io/doc/org.seleniumhq.selenium/selenium-support/latest/org/openqa/selenium/support/ui/ExpectedConditions.html">...</a>
 * @author tisaac
 */
public class BasePage {
    protected WebDriver driver; // WebDriver instance used for all tests
    protected WebDriverWait wait; // WebDriverWait instance for explicit waits

    // Constructor to initialize WebDriver and WebDriverWait
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 seconds timeout for waits
    }

    // Click an element
    public void click(By locator) {
        // By is a locator strategy used in Selenium to find elements on a webpage.
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
    }

    // Enter text
    public void enterText(By locator, String txt) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        // Check if element is editable
        try {
            if (!element.isEnabled()) {
                throw new IllegalStateException("Element is not enabled for input");
            }

            element.clear(); // Clear the text field before entering text
            element.sendKeys(txt); // Enter the text
        } catch (IllegalStateException e) {
            System.out.println("Element is not enabled for input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while entering text: " + e.getMessage());
        }
    }

    // Retrieve text from an element
    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // Check if an element is present
    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false; // Element not found
        }
    }

    // Wait until an element is available
    public void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // When a website contains iframes, Selenium cannot directly interact with elements inside the iframe unless you switch into it first.
    // Duo authentication appears inside an iframe. Selenium must switch to the iframe to interact with its elements.
    public void switchToFrame(By locator) {
        WebElement frameElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.switchTo().frame(frameElement);
    }

    // Once authentication is done, Selenium must switch back to the main content.
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}
