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
import com.example.batch25.model.Division;
import com.example.batch25.repository.DivisionRepository;

@RestController
@RequestMapping("api")
public class DivisionRestController {
    @Autowired
    DivisionRepository divisionRepository;

    @GetMapping("division")
    public ResponseEntity<Object> Get(){
        return CustomResponse.generate(HttpStatus.OK, "Data retrieved", divisionRepository.findAll());
    }

    @PostMapping(value = {"division", "division/{id}"})
    public ResponseEntity<Object> save(@RequestBody Division division, @PathVariable(required = false) Integer id){
        if (id != null){
            Division newDivision  = divisionRepository.findById(id).orElse(null);
            newDivision.setId(division.getId());
            newDivision.setName(division.getName());
            newDivision.setDepartments(division.getDepartments());
            divisionRepository.save(newDivision);
            return CustomResponse.generate(HttpStatus.OK, "Data saved");
        }else{
            divisionRepository.save(division);
            Boolean isCreated = divisionRepository.findById(division.getId()).isPresent();
            if(isCreated){
                return CustomResponse.generate(HttpStatus.OK, "Data saved");
            } 
        }return CustomResponse.generate(HttpStatus.OK, "Data failed to save");
    }

    @DeleteMapping("division/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        divisionRepository.deleteById(id);
        Boolean isDeleted = divisionRepository.findById(id).isEmpty();
        if(isDeleted){
            return CustomResponse.generate(HttpStatus.OK, "Data deleted");
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to delete");
        }
        
    }

    @GetMapping("division/{id}")
    public ResponseEntity<Object> Get(@PathVariable(required = true) Integer id){
        divisionRepository.findById(id);
        Boolean isExist = divisionRepository.findById(id).isPresent();
        if(isExist){
            return CustomResponse.generate(HttpStatus.OK, "Data retrieved", divisionRepository.findById(id));
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to retrieve");
        }
    }
}
