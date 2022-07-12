package com.example.Excel.service;

import com.example.Excel.model.DataObject;
import com.example.Excel.model.PeopleReadExcel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IService {
    DataObject readExcel(MultipartFile file) throws IOException;
    PeopleReadExcel create(PeopleReadExcel demo);
}
