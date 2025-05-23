package com.isaactai.selenium.pages;

import com.isaactai.selenium.base.BasePage;
import com.isaactai.selenium.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
/**
 * @author tisaac
 */
public class TranscriptPage extends BasePage {

    private By transcriptLevel = null;
    private By transcriptType = null;
    private By submitBtn = null;
    private By transcriptContainer = null;

    public TranscriptPage(WebDriver driver) {
        super(driver);
        loadLocator();
        logger.debug("transcript page initialized");
    }

    // Select Transcript Level as “Graduate” and Transcript Type as “Audit Transcript”.
    public void selectTranscriptOptions(String level, String type) {
        selectFromDropdown(transcriptLevel, level);
        selectFromDropdown(transcriptType, type);
        click(submitBtn);
    }

    public void waitForTranscriptShow() {
        // wait for transcript to show
        logger.debug("Waiting for transcript to show");
        waitUntilVisible(transcriptContainer);
    }

    public void saveCurrentPageAsPDF(String filePath) throws IOException {
        if (!(driver instanceof ChromeDriver)) {
            throw new UnsupportedOperationException("PDF export requires ChromeDriver.");
        }

        Map<String, Object> printOptions = new HashMap<>();
        printOptions.put("printBackground", true);

        Map<String, Object> result = ((ChromeDriver) driver).executeCdpCommand("Page.printToPDF", printOptions);
        String base64 = (String) result.get("data");

        byte[] pdfBytes = Base64.getDecoder().decode(base64);
        File outputFile = new File(filePath);
        outputFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(pdfBytes);
        }
    }

    public void loadLocator() {
        String excelSheetName = "TranscriptTest";
        transcriptLevel = By.id(ExcelUtil.getCellValue(excelSheetName, "transcriptLevel", "LocatorValue"));
        transcriptType = By.id(ExcelUtil.getCellValue(excelSheetName, "transcriptType", "LocatorValue"));
        submitBtn = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "submitBtn", "LocatorValue"));
        transcriptContainer = By.cssSelector(ExcelUtil.getCellValue(excelSheetName, "transcriptContainer", "LocatorValue"));
    }
}
