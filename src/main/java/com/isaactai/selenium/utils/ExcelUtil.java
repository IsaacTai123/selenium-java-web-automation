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
import java.util.HashMap;
import java.util.Map;

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
                        return getCellAsString(cell);
                    }
                }
            }
        }
        return null;
    }

    private static String getCellAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // ✅ 假設都是整數
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    // Get the row data as a map
    public static Map<String, String> getRowData(String sheetName, String fieldName) {
        if (workbook == null) {
            throw new IllegalStateException("Excel workbook is not loaded. Call ExcelUtil.loadExcel(filePath) first.");
        }

        Sheet sheet = workbook.getSheet(sheetName);
        Map<String, String> rowData = new HashMap<>();

        Row headerRow = sheet.getRow(0); // 第一列是欄位名稱
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            if (keyCell != null && keyCell.getStringCellValue().equals(fieldName)) {
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell headerCell = headerRow.getCell(i);
                    Cell valueCell = row.getCell(i);

                    if (headerCell != null) {
                        String key = headerCell.getStringCellValue();
                        String value = (valueCell != null) ? valueCell.toString() : "";
                        rowData.put(key, value);
                    }
                }
                return rowData; // 找到就回傳
            }
        }

        return null; // 沒找到就回 null
    }
}
