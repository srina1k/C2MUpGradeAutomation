package org.example.Utils;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    private static Sheet sheet;
    private static Workbook workbook;
    private static String excelPath;
    private static FileInputStream fis;
    private static FileOutputStream fos;
    public static void loadExcel(String filePath, String sheetName){
        try{ excelPath = filePath;
            FileInputStream fis =  new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet(sheetName);
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getCellData(int rowNum, int colNum){
        Cell cell = sheet.getRow(rowNum).getCell(colNum);
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
    public static void setCellData(int rowNum, int colNum, String value) throws IOException {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }

        cell.setCellValue(value);


        try (FileOutputStream fos = new FileOutputStream(excelPath)) {
            workbook.write(fos);
        }
    }
    public static void openWorkBook(String filePath, String sheetName) {
        try {
            excelPath = filePath;
            File file = new File(filePath);
            if (file.exists()) {
                fis = new FileInputStream(file);
                workbook = WorkbookFactory.create(fis);
            } else {
                workbook = WorkbookFactory.create(true);
            }
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to open Excel file", e);
        }
    }
    public static void saveAndClose() {
        try {
            fis.close();

            fos = new FileOutputStream(excelPath);
            workbook.write(fos);
            fos.flush();
            fos.close();
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save", e);
        }
    }
}
