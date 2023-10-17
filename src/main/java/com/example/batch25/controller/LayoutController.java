package com.example.batch25.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("layout")
public class LayoutController {

    @GetMapping()
    public String index(Model model){
        return "layout/index";
    }
}
