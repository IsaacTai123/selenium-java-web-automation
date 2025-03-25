package com.isaactai.selenium.utils;

import com.isaactai.selenium.pages.MicrosoftLoginPage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author tisaac
 */
public class ExcelUtil {

    protected static final Logger logger = LoggerFactory.getLogger(MicrosoftLoginPage.class);
    private static Workbook workbook;

    public static void loadExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            logger.error("Failed to load Excel file: {}", filePath, e);
        }
    }

    public static String getCellValue(String sheetName, String fieldName, String columnName) {
        if (workbook == null) {
            throw new IllegalStateException("Excel workbook is not loaded. Call ExcelUtil.loadExcel(filePath) first.");
        }

        Sheet sheet = workbook.getSheet(sheetName);
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            if (keyCell != null && keyCell.getStringCellValue().equals(fieldName)) {
                for (Cell cell : row) {
                    if (sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue().equals(columnName)) {
                        return cell.getStringCellValue();
                    }
                }
            }
        }
        return null;
    }
}
