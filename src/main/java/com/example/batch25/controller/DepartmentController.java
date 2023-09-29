package com.example.batch25.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.batch25.model.Department;
import com.example.batch25.repository.DepartmentRepository;
import com.example.batch25.repository.DivisionRepository;
import com.example.batch25.repository.RegionRepository;

@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    DivisionRepository divisionRepository;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/index";
    }

    @GetMapping(value = {"form", "form/{id}"})
    public String form(Model model, @PathVariable(required = false) Integer id){
        if (id != null){
            model.addAttribute("department", departmentRepository.findById(id));
            model.addAttribute("regions", regionRepository.findAll());
            model.addAttribute("divisions", divisionRepository.findAll());
        }else{
            model.addAttribute("department", new Department());
            model.addAttribute("regions", regionRepository.findAll());
            model.addAttribute("divisions", divisionRepository.findAll());
        }
        return "department/form";
    }

    @PostMapping("save")
    public String save(Department department){
        departmentRepository.save(department);
        if(departmentRepository.findById(department.getId()).isPresent()){
            return "redirect:/department";
        }else{
            return "department/form";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable(required = true) Integer id){
        departmentRepository.deleteById(id);
        Boolean isDeleted = departmentRepository.findById(id).isEmpty();
        if(isDeleted){
            System.out.println("Data deleted");
        }else{
            System.out.println("Failed to delete data");
        }
        return "redirect:/department";
    }
}
