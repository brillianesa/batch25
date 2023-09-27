package com.example.batch25.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.batch25.model.Region;
import com.example.batch25.repository.RegionRepository;

@Controller
@RequestMapping("region")
public class RegionController {
    @Autowired
    RegionRepository regionRepository;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("regions", regionRepository.findAll());
        return "region/index";
    }

    @GetMapping("form")
    public String form(Model model){
        model.addAttribute("region", new Region());
        return "region/form";
    }
    

    @PostMapping("save")
    public String save(Region region){
        regionRepository.save(region);
        if(regionRepository.findById(region.getId()).isPresent()){
            return "redirect:/region";
        }else{
            return "region/form";
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id){
        regionRepository.deleteById(id);
        Boolean isDeleted = regionRepository.findById(id).isEmpty();
        if(isDeleted){
            System.out.println("Data deleted");
        }else{
            System.out.println("Failed to delete data");
        }
        return "redirect:/region";
    }


}
