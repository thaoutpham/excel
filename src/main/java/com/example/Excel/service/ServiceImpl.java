package com.example.Excel.service;

import com.example.Excel.model.DataObject;
import com.example.Excel.model.IndexError;
import com.example.Excel.model.People;
import com.example.Excel.repository.DemoRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceImpl implements IService {

    @Autowired
    private DemoRepository demoRepository;

    @Override
    public DataObject readExcel(MultipartFile file) throws IOException {// đọc file excel
        DataObject dataObject = new DataObject();
        List<IndexError> indexErrorList = new ArrayList<>();
        List<People> peopleList = new ArrayList<>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);

            int number = 0;
            People people = new People();

            try {
               long uniqueId = (long) row.getCell(0).getNumericCellValue();
                number++;
                people.setUniqueID(String.valueOf(uniqueId));
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                if (number == 0) {
                    indexErrorList.add(createIndexError(i, 0));
                    people.setUniqueID(row.getCell(0).getStringCellValue());
                }

            }
            number = 0;


            try {
                String title = row.getCell(1).getStringCellValue();
                if (title.isEmpty() || title.length() <= 2 || title.length() >= 50) {
                    indexErrorList.add(createIndexError(i, 1));
                    people.setTitle(row.getCell(1).getStringCellValue());
                }
                number++;
                people.setTitle(title);
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                if (number == 0) {
                    indexErrorList.add(createIndexError(i, 1));
                    people.setTitle(row.getCell(1).getStringCellValue());
                }
            }
            number = 0;



            try {
                String name = row.getCell(2).getStringCellValue();
                if (name.isEmpty() || name.length() <= 2 || name.length() >= 50) {
                    indexErrorList.add(createIndexError(i, 2));
                    people.setName(name);
                }
                number++;
                people.setName(name);
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                if (number == 0) {
                    indexErrorList.add(createIndexError(i, 2));
                    people.setName(row.getCell(2).getStringCellValue());
                }
            }
            number = 0;

            try {
                Date dateOfBirth = row.getCell(3).getDateCellValue();
                Date date = new Date();
                if (dateOfBirth.compareTo(date) >= 0) {
                    indexErrorList.add(createIndexError(i, 3));
                    people.setDate(row.getCell(3).getStringCellValue());
                }
                number++;
                people.setDate(convertStringToDate(dateOfBirth));
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                if (number == 0) {
                    indexErrorList.add(createIndexError(i, 3));
                    people.setDate(row.getCell(3).getStringCellValue());
                }
            }


            peopleList.add(people);
        }

        System.out.println(indexErrorList.toString());
        dataObject.setList(peopleList);
        dataObject.setIndexErrorList(indexErrorList);
        return dataObject;

    }

    public Long convertStringToLong(String data) throws IOException {
        return Long.parseLong(data);
    }

    public IndexError createIndexError(int row, int columns) {
        IndexError indexError = new IndexError();
//        row++;
        indexError.setRow(row);
//        columns++;
        indexError.setColumns(columns);
        return indexError;
    }

    public Boolean validateString(String data) throws IOException {
        if (!data.isEmpty() && data.length() >= 2 && data.length() <= 50) {
            return true;
        }
        return false;
    }

    public String convertStringToDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String data = (formatter.format(date));
        return data;
    }


}