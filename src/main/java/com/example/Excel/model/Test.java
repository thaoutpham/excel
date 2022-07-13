package com.example.Excel.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String startDate = "27/06/2007";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date todayDate = df.parse(startDate);
            System.out.println(todayDate);


            LocalDate localDate = Instant.ofEpochMilli(todayDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            System.out.println(localDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
