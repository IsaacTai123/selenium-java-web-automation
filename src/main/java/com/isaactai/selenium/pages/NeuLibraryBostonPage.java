package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * @author tisaac
 */
public class NeuLibraryBostonPage extends BasePage {

    private final By digitalRepoServiceBtn = By.cssSelector("a[aria-label^='digital repository service']");
    private final By datasetsBtn = By.cssSelector("a[href='/datasets']");
    private final By zipDownloadBtn = By.cssSelector("a[href=\"/downloads/neu:4f24kd24s?datastream_id=content\"]");

    public NeuLibraryBostonPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToDigitalRepositoryService() {
        click(digitalRepoServiceBtn);
        switchToNewWindow();
        scrollToElement(datasetsBtn);
        click(datasetsBtn);
    }

    public void downloadZipFile() {
        click(zipDownloadBtn);
    }

    public String getFileName() {
        WebElement zipLink = driver.findElement(zipDownloadBtn);
        String href = zipLink.getAttribute("href");
        String fileName = href.substring(href.lastIndexOf("/") + 1).split("\\?")[0];
        fileName = fileName.replaceAll("[^a-zA-Z0-9]", "_"); // Replace special characters with underscores
        if (!fileName.endsWith(".zip")) fileName += ".zip";

        return fileName;
    }
}
