package com.example.batch25.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.batch25.dto.RegisterRequest;
import com.example.batch25.model.Employee;
import com.example.batch25.model.User;
import com.example.batch25.repository.DepartmentRepository;
import com.example.batch25.repository.EmployeeRepository;
import com.example.batch25.repository.RoleRepository;
import com.example.batch25.repository.UserRepository;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository; 

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee/index";
    }

    @GetMapping(value = {"form", "form/{id}"})
    public String form(Model model, @PathVariable(required = false) Integer id){
        if (id != null){
            model.addAttribute("employee", employeeRepository.findById(id));
            model.addAttribute("user", userRepository.findById(id));
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("roles", roleRepository.findAll());
        }else{
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("registerDTO", new RegisterRequest());
            // model.addAttribute("employee", new Employee());
            // model.addAttribute("user", new User());
        }
        return "employee/form";
    }

    @PostMapping("save")
    public String save(RegisterRequest registerRequest){
        Employee emp = new Employee();
        emp.setFullname(registerRequest.getFullname());
        emp.setJoindate(registerRequest.getJoindate());
        emp.setNumberphone(registerRequest.getNumberphone());
        emp.setDepartment(registerRequest.getDepartment());
        employeeRepository.save(emp);
        if(employeeRepository.findById(emp.getId()).isPresent()){
            User user = new User();
            Integer user_id = employeeRepository.findIdByPhoneNumber(registerRequest.getNumberphone());
            user.setId(user_id);
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(registerRequest.getRole());
            userRepository.save(user);
            if(userRepository.findById(user.getId()).isPresent()){
                System.out.println("Data berhasil disimpan");
                return "redirect:/employee";
            }
        }else{
            return "employee/form";
        }
        return "employee/form";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable(required = true) Integer id){
        employeeRepository.deleteById(id);
        Boolean isDeleted = employeeRepository.findById(id).isEmpty();
        if(isDeleted){
            userRepository.deleteById(id);
            Boolean isUserDeleted = userRepository.findById(id).isEmpty();
            if(isUserDeleted){
                System.out.println("Data deleted");
            }
        }else{
            System.out.println("Failed to delete data");
        }
        return "redirect:/employee";
    }
}
