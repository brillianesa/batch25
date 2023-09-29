package com.example.batch25.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    @Query(value = "SELECT employee_id FROM tb_m_employee WHERE numberphone = ?1", nativeQuery = true)
    public Integer findIdByPhoneNumber(String numberphone);
}
