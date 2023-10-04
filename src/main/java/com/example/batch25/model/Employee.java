package com.example.batch25.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_m_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    private String fullname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joindate;
    private String numberphone;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", fullname=" + fullname + ", joindate=" + joindate + ", numberphone="
                + numberphone + ", department=" + department + "]";
    }

    public Employee(){
        
    }

    public Integer getId() {
        return id;
    }

    public Employee(Integer id, String fullname, Date joindate, String numberphone, Department department) {
        this.id = id;
        this.fullname = fullname;
        this.joindate = joindate;
        this.numberphone = numberphone;
        this.department = department;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    
}
