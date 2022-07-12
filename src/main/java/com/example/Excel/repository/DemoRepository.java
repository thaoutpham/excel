package com.example.Excel.repository;


import com.example.Excel.model.PeopleReadExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends JpaRepository<PeopleReadExcel,Long> {
}
