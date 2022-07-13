package com.example.Excel.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "tutorials")
public class Tutorial {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "published")
    private boolean published;

    public Tutorial(long id, String title, LocalDate date, boolean published) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.published = published;
    }

    public Tutorial() {
    }
}