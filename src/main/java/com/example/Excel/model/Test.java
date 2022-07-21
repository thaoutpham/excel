package com.example.Excel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
       String abc = "hello";
        // so sánh hello với null thì false;
        // so sánh chuỗi null với chuỗi thì lỗi vì nó không tìm thấy value của chuỗi abc để so sánh
        // so sánh equals là so sánh value
        //so sánh == là so sánh cảu value và vị trí lưu trữ
        String a =  new String("hello");
       boolean check = abc.equals(a);
        System.out.println(abc == a );
        System.out.println(check);
    }
}
