package org.example.Utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    private static Sheet sheet;
    private static Workbook workbook;
    private static String excelPath;

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
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(value);

        } catch (Exception e){
            e.printStackTrace();
        }
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);
        workbook.close();
    }
}
