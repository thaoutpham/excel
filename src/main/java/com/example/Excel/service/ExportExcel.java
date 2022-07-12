package com.example.Excel.service;


import com.example.Excel.model.IndexError;
import com.example.Excel.model.People;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExportExcel { // viết vào file excel
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<People> list;
    private List<IndexError> listIndexError;

    public ExportExcel(List<People> list, List<IndexError> listIndexError) {
        this.list = list;
        this.listIndexError = listIndexError;
        workbook = new XSSFWorkbook();

    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "UniqueID", style);
        createCell(row, 1, "Title", style);
        createCell(row, 2, "Name", style);
        createCell(row, 3, "Date", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        CellStyle styleError = workbook.createCellStyle();
        XSSFFont fontError = workbook.createFont();
        fontError.setFontHeight(14);
        fontError.setColor(IndexedColors.RED.getIndex());
        styleError.setFont(fontError);

        for (int i = 0; i < list.size(); i++) {
            People people = list.get(i);
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            boolean checkRowError = false;

            int rowError = 0;
            for (int y = rowError; y < listIndexError.size(); y++) {
                if (listIndexError.get(y).getRow() == i +1) {
                    checkRowError = true;
                    rowError = y;
                }
            }
            if (checkRowError && columnCount == listIndexError.get(rowError).getColumns()) {
                createCell(row, columnCount++, people.getUniqueID(), styleError);
            } else {
                createCell(row, columnCount++, people.getUniqueID(), style);
            }

            if (checkRowError && columnCount == listIndexError.get(rowError).getColumns()) {
                createCell(row, columnCount++, people.getTitle(), styleError);
            } else {
                createCell(row, columnCount++, people.getTitle(), style);
            }

            if (checkRowError && columnCount == listIndexError.get(rowError).getColumns()) {
                createCell(row, columnCount++, people.getName(), styleError);
            } else {
                createCell(row, columnCount++, people.getName(), style);
            }
            if (checkRowError && columnCount == listIndexError.get(rowError).getColumns()) {
                createCell(row, columnCount++, people.getDate(), styleError);
            } else {
                createCell(row, columnCount++, people.getDate(), style);
            }

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}
