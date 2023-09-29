package com.example.batch25.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.batch25.model.Division;

import com.example.batch25.repository.DivisionRepository;

@Controller
@RequestMapping("division")
public class DivisionController {
    @Autowired
    DivisionRepository divisionRepository;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("divisions", divisionRepository.findAll());
        return "division/index";
    }

    @GetMapping(value = {"form", "form/{id}"})
    public String form(Model model, @PathVariable(required = false) Integer id){
        if (id != null){
            model.addAttribute("division", divisionRepository.findById(id));
        }else{
            model.addAttribute("division", new Division());
        }
        return "division/form";
    }

    @PostMapping("save")
    public String save(Division division){
        divisionRepository.save(division);
        if(divisionRepository.findById(division.getId()).isPresent()){
            return "redirect:/division";
        }else{
            return "division/form";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable(required = true) Integer id){
        divisionRepository.deleteById(id);
        Boolean isDeleted = divisionRepository.findById(id).isEmpty();
        if(isDeleted){
            System.out.println("Data deleted");
        }else{
            System.out.println("Failed to delete data");
        }
        return "redirect:/division";
    }
}
