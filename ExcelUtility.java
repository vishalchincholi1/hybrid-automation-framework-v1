package com.automation.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel Utility for reading and writing Excel files
 * Supports data-driven testing with Excel data sources
 */
public class ExcelUtility {
    private Workbook workbook;
    private Sheet sheet;
    private String filePath;
    
    /**
     * Constructor to initialize Excel utility
     * @param filePath Excel file path
     */
    public ExcelUtility(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }
    
    /**
     * Set active sheet by name
     * @param sheetName Sheet name
     */
    public void setSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
    }
    
    /**
     * Set active sheet by index
     * @param sheetIndex Sheet index (0-based)
     */
    public void setSheet(int sheetIndex) {
        sheet = workbook.getSheetAt(sheetIndex);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found at index: " + sheetIndex);
        }
    }
    
    /**
     * Get row count
     * @return Number of rows
     */
    public int getRowCount() {
        return sheet.getLastRowNum() + 1;
    }
    
    /**
     * Get column count
     * @return Number of columns
     */
    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }
    
    /**
     * Get cell data as string
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @return Cell value as string
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return "";
        }
        
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            return "";
        }
        
        return getCellValueAsString(cell);
    }
    
    /**
     * Get cell data by column name
     * @param rowNum Row number (0-based)
     * @param columnName Column name
     * @return Cell value as string
     */
    public String getCellData(int rowNum, String columnName) {
        int colNum = getColumnIndex(columnName);
        return getCellData(rowNum, colNum);
    }
    
    /**
     * Set cell data
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @param data Data to set
     */
    public void setCellData(int rowNum, int colNum, String data) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        
        cell.setCellValue(data);
    }
    
    /**
     * Set cell data by column name
     * @param rowNum Row number (0-based)
     * @param columnName Column name
     * @param data Data to set
     */
    public void setCellData(int rowNum, String columnName, String data) {
        int colNum = getColumnIndex(columnName);
        setCellData(rowNum, colNum, data);
    }
    
    /**
     * Get all data from sheet as 2D array
     * @return 2D string array with all data
     */
    public String[][] getAllData() {
        int rowCount = getRowCount();
        int colCount = getColumnCount();
        
        String[][] data = new String[rowCount - 1][colCount]; // Exclude header row
        
        for (int i = 1; i < rowCount; i++) { // Start from row 1 (skip header)
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = getCellData(i, j);
            }
        }
        
        return data;
    }
    
    /**
     * Get test data as list of maps (column name -> value)
     * @return List of test data maps
     */
    public List<Map<String, String>> getTestDataAsMaps() {
        List<Map<String, String>> testData = new ArrayList<>();
        int rowCount = getRowCount();
        int colCount = getColumnCount();
        
        // Get header row
        List<String> headers = new ArrayList<>();
        for (int j = 0; j < colCount; j++) {
            headers.add(getCellData(0, j));
        }
        
        // Get data rows
        for (int i = 1; i < rowCount; i++) {
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                rowData.put(headers.get(j), getCellData(i, j));
            }
            testData.add(rowData);
        }
        
        return testData;
    }
    
    /**
     * Get specific test data by test case name
     * @param testCaseName Test case name
     * @param testCaseColumn Column name containing test case names
     * @return Test data map
     */
    public Map<String, String> getTestDataByName(String testCaseName, String testCaseColumn) {
        List<Map<String, String>> allData = getTestDataAsMaps();
        
        for (Map<String, String> rowData : allData) {
            if (testCaseName.equals(rowData.get(testCaseColumn))) {
                return rowData;
            }
        }
        
        throw new RuntimeException("Test case not found: " + testCaseName);
    }
    
    /**
     * Get column index by column name
     * @param columnName Column name
     * @return Column index (0-based)
     */
    private int getColumnIndex(String columnName) {
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getLastCellNum();
        
        for (int i = 0; i < colCount; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && columnName.equals(getCellValueAsString(cell))) {
                return i;
            }
        }
        
        throw new RuntimeException("Column not found: " + columnName);
    }
    
    /**
     * Convert cell value to string
     * @param cell Excel cell
     * @return Cell value as string
     */
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
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
    
    /**
     * Save workbook to file
     */
    public void saveWorkbook() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Excel file: " + filePath, e);
        }
    }
    
    /**
     * Close workbook
     */
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel file", e);
        }
    }
    
    /**
     * Create new sheet
     * @param sheetName Sheet name
     */
    public void createSheet(String sheetName) {
        workbook.createSheet(sheetName);
    }
    
    /**
     * Delete sheet
     * @param sheetName Sheet name
     */
    public void deleteSheet(String sheetName) {
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex >= 0) {
            workbook.removeSheetAt(sheetIndex);
        }
    }
    
    /**
     * Get all sheet names
     * @return List of sheet names
     */
    public List<String> getSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        int numberOfSheets = workbook.getNumberOfSheets();
        
        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames.add(workbook.getSheetName(i));
        }
        
        return sheetNames;
    }
}
