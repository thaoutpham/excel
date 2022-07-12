package com.example.Excel.model;

import lombok.Data;

import java.util.List;

@Data
public class DataObject {
    private List<People> list;
    private List<IndexError>indexErrorList;
}
