package com.example.Excel.pdf;

import com.example.Excel.model.Tutorial;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {// kiểm tra xem có phải file text hay csv không
            return false;
        }
        return true;
    }
    public static List<Tutorial> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {//withFirstRecordAsHeader lấy dòng đầu tiên làm tiêu đề,withIgnoreHeaderCase lấy dữ liệu bỏ qua dòng tiêu đề
            List<Tutorial> tutorials = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Tutorial tutorial = new Tutorial(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Title"),
                        convertStringToDate(csvRecord.get("Date")),
                        Boolean.parseBoolean(csvRecord.get("Published"))
                );
                tutorials.add(tutorial);
            }
            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static LocalDate convertStringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}
