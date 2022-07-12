package com.example.Excel.controller;

import com.example.Excel.model.DataObject;
import com.example.Excel.service.ExportExcel;
import com.example.Excel.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/excel")
@CrossOrigin("*")
public class ReadExcelController {
    @Autowired
    private ServiceImpl demoService;

    private DataObject dataObject;

    @PostMapping("/read")
    public String excelReader(@RequestParam("file") MultipartFile excel) {
        try {
            dataObject = demoService.readExcel(excel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dataObject.getIndexErrorList().size() == 0) {
            return "Success";
        } else {
            return "http://localhost:8080/excel/export";
        }

    }


    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=export_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            if (dataObject.getIndexErrorList().size() != 0) {
                ExportExcel excelExporter = new ExportExcel(dataObject.getList(), dataObject.getIndexErrorList());
                excelExporter.export(response);
            }
        } catch (NullPointerException e) {
            System.out.println("Error========" + e.getMessage());
        }

    }
}
