package com.example.batch25.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_m_region")
public class Region {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    @Column(name = "region_id") // Penamaan Column di Database
    private Integer region_id;
    private String name;

    @OneToMany(mappedBy = "region")
    public List<Department> departments;


    public Integer getRegion_id() {
        return region_id;
    }
    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
