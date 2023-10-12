package com.example.batch25.controller.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch25.handler.CustomResponse;
import com.example.batch25.model.Department;
import com.example.batch25.model.Region;
import com.example.batch25.repository.DepartmentRepository;

@RestController
@RequestMapping("api")
public class DepartmentRestController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("department")
    public ResponseEntity<Object> Get(){
        return CustomResponse.generate(HttpStatus.OK, "Data retrieved", departmentRepository.findAll());
    }

    @PostMapping(value = {"department", "department/{id}"})
    public ResponseEntity<Object> save(@RequestBody Department department, @PathVariable(required = false) Integer id){
        if (id != null){
            Department newDepartment  = departmentRepository.findById(id).orElse(null);
            newDepartment.setId(id);
            newDepartment.setName(department.getName());
            newDepartment.setRegion(department.getRegion());
            newDepartment.setDivision(department.getDivision());
            departmentRepository.save(newDepartment);
            return CustomResponse.generate(HttpStatus.OK, "Data saved");
        }else{
            departmentRepository.save(department);
            Boolean isCreated = departmentRepository.findById(department.getId()).isPresent();
            if(isCreated){
                return CustomResponse.generate(HttpStatus.OK, "Data saved");
            } 
        }return CustomResponse.generate(HttpStatus.OK, "Data failed to save");
    }

    @DeleteMapping("department/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        departmentRepository.deleteById(id);
        Boolean isDeleted = departmentRepository.findById(id).isEmpty();
        if(isDeleted){
            return CustomResponse.generate(HttpStatus.OK, "Data deleted");
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to delete");
        }
        
    }

    @GetMapping("department/{id}")
    public ResponseEntity<Object> Get(@PathVariable(required = true) Integer id){
        departmentRepository.findById(id);
        Boolean isExist = departmentRepository.findById(id).isPresent();
        if(isExist){
            return CustomResponse.generate(HttpStatus.OK, "Data retrieved", departmentRepository.findById(id));
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to retrieve");
        }
    }
}
