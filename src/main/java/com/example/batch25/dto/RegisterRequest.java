package com.example.batch25.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.batch25.model.Department;
import com.example.batch25.model.Role;

public class RegisterRequest {
    private String fullname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joindate;
    private String numberphone;
    private Department department;
    private String email;
    private String password;
    private Role role;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    
}
