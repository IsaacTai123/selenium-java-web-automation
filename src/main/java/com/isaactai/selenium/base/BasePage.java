package com.isaactai.selenium.base;

import com.isaactai.selenium.pages.MicrosoftLoginPage;
import com.isaactai.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * BasePage class provides common methods for interacting with web elements.
 * Check more details at <a href="https://www.javadoc.io/doc/org.seleniumhq.selenium/selenium-support/latest/org/openqa/selenium/support/ui/ExpectedConditions.html">seleniumhq docs</a>
 *
 * @author tisaac
 */
public class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(MicrosoftLoginPage.class);
    protected WebDriver driver; // WebDriver instance used for all tests
    protected WebDriverWait wait; // WebDriverWait instance for explicit waits
    protected String originalWindow;

    // Constructor to initialize WebDriver and WebDriverWait
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 seconds timeout for waits
    }

    // Click an element
    public void click(By locator) {
        // By is a locator strategy used in Selenium to find elements on a webpage.
        WebElement element = waitForElementToBeClickable(locator);
        ScreenshotUtil.takeScreenshot(driver, "before_click" + sanitize(locator.toString()));
        try {
            element.click(); // try to click the element
        } catch (ElementClickInterceptedException e) {
            jsClick(element); // if intercepted, use JavaScript to click
        }
        ScreenshotUtil.takeScreenshot(driver, "after_click" + sanitize(locator.toString()));
    }

    public void clickByElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ScreenshotUtil.takeScreenshot(driver, "before_click" + sanitize(element.toString()));
        try {
            element.click(); // try to click the element
        } catch (ElementClickInterceptedException e) {
            jsClick(element); // if intercepted, use JavaScript to click
        }
        ScreenshotUtil.takeScreenshot(driver, "after_click" + sanitize(element.toString()));
    }

    public void clickIfPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            WebElement element = elements.get(0);

            if (element.isDisplayed()) {
                logger.info("Element is visible: {}", locator);
                click(locator);
            } else {
                logger.info("Element found but not visible: skipping click for " + locator);
            }
        } else {
            logger.info("Element not found: skipping click for " + locator);
        }
    }

    /**
     * Enters text into an input field after clearing it properly.
     * Handles both macOS and Windows control keys (COMMAND vs CONTROL).
     *
     * @param locator The locator of the input field.
     * @param text    The text to be entered.
     */
    public void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        // Check if element is editable
        try {
            if (!element.isEnabled()) {
                throw new IllegalStateException("Element is not enabled for input");
            }

            // Check if field is readonly
            String readonly = element.getAttribute("readonly");
            if (readonly != null && readonly.equalsIgnoreCase("true")) {
                logger.warn("Input field is readonly and cannot be typed into: {}", locator);
                return;
            }

            // Clear field using platform-appropriate shortcut
            clearInputCrossPlatform(element);

            element.clear(); // Clear the text field before entering text
            element.sendKeys(text); // Enter the text
            logger.debug("Input entered: [{}] -> {}", locator, text);

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

    // Select from dropdown
    public void selectFromDropdown(By locator, String item) {
        WebElement dropdown = waitUntilVisible(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(item);
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
    public WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void switchToNewWindow() {
        originalWindow = driver.getWindowHandle(); // remember current window
        wait.until(driver -> driver.getWindowHandles().size() > 1); // wait for the second window to be opened

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle); // jump to the new window
                break;
            }
        }
    }

    public void switchBackToOriginalWindow() {
        if (originalWindow != null) {
            driver.switchTo().window(originalWindow);
        } else {
            throw new IllegalStateException("originalWindow is not set.");
        }
    }

    public String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }

    /**
     * Selects an item from a custom (non-&lt;select&gt;) dropdown.
     * This method works for dropdowns built with an &lt;input&gt; element and a list of options (e.g., Canvas Frequency).
     *
     * @param dropdownInput the input element that opens the dropdown (usually an &lt;input&gt; or &lt;div&gt;)
     * @param optionText    the visible text of the option to be selected (e.g., "Daily")
     */
    public void selectFromCustomDropdown(By dropdownInput, String optionText) {
        // 1. Click the input to open the dropdown
        click(dropdownInput);
        logger.debug("Opened dropdown for: {}", dropdownInput);

        // 2. Build locator for the visible option
        By optionLocator = By.xpath("//span[@role='option' and normalize-space()='" + optionText + "']");

        // 3. Wait for and click the desired option
        click(optionLocator);
        logger.debug("Selected option: {}", optionText);
    }

    /**
     * Clears the content of an input field using platform-specific key commands.
     *
     * @param element The input WebElement to be cleared.
     */
    public void clearInputCrossPlatform(WebElement element) {
        Keys commandKey = System.getProperty("os.name").toLowerCase().contains("mac")
                ? Keys.COMMAND
                : Keys.CONTROL;

        // Select all and delete to clear the input field
        element.sendKeys(Keys.chord(commandKey, "a"));
        element.sendKeys(Keys.DELETE);
    }

    public void scrollToBottomAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        clickByElement(element);
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void switchToIframe(String idOrName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
        logger.debug("Switched to iframe: " + idOrName);
    }

    // Switch to iframe using locator
    public void switchToIframe(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
        logger.debug("Switched to iframe using locator: " + locator);
    }

    // Switch to iframe using WebElement
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.debug("Switched back to default content");
    }
}
