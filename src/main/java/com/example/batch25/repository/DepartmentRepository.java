package com.example.batch25.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    
}
