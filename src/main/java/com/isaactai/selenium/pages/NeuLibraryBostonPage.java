package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author tisaac
 */
public class NeuLibraryBostonPage extends BasePage {

    private By digitalRepoServiceBtn = null;
    private By datasetsBtn = null;
    private By zipDownloadBtn = null;

    public NeuLibraryBostonPage(WebDriver driver) {
        super(driver);
        loadLocator();
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

    public void loadLocator() {
        // Load locators from the Excel file
        String excelSheetName = "DatasetsTest";
        digitalRepoServiceBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "digitalRepoServiceBtn", "LocatorValue"));
        datasetsBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "datasetsBtn", "LocatorValue"));
        zipDownloadBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "zipDownloadBtn", "LocatorValue"));
    }
}
