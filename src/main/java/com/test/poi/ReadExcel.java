package com.test.poi;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class ReadExcel {
    public static void main(String[] args) {
        readExcel("src/main/java/com/test/poi/testcase.xlsx", "resources/apicase");
    }
    public static void readExcel(String excelPath, String sheetName){
        FileInputStream in = null;
        try {
            File file = new File(excelPath);
            in = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheet(sheetName);
            Row firstRow = sheet.getRow(0);
            int lastCellNum = firstRow.getLastCellNum();
            String[] titles = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = firstRow.getCell(i);
                String title = cell.getStringCellValue();
                titles[i] = title;
            }
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum ; i++) {
                Row rowData = sheet.getRow(i);
                System.out.print("第"+i+"行数据：");
                for (int j = 1; j < lastCellNum ; j++) {
                    Cell cell = rowData.getCell(j);
                    String cellValue = cell.getStringCellValue();
                    // 打印获取到的值
                    System.out.print("【"+ titles[j] + "="+ cellValue+"】");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
