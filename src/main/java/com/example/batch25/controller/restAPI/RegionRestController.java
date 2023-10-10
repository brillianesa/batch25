package com.example.batch25.controller.restAPI;

import java.util.List;
import java.util.Optional;

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
import com.example.batch25.model.Region;
import com.example.batch25.repository.RegionRepository;

@RestController
@RequestMapping("api")
public class RegionRestController {
    @Autowired
    private RegionRepository regionRepository;


    @GetMapping("region")
    public ResponseEntity<Object> Get(){
        return CustomResponse.generate(HttpStatus.OK, "Data retrieved", regionRepository.findAll());
    }

    @PostMapping(value = {"region", "region/{id}"})
    public ResponseEntity<Object> save(@RequestBody Region region, @PathVariable(required = false) Integer id){
        if (id != null){
            Region newRegion  = regionRepository.findById(id).orElse(null);
            newRegion.setId(id);
            newRegion.setName(region.getName());
            regionRepository.save(newRegion);
            Boolean isUpdated = regionRepository.CountByName(newRegion.getName()) > 0 && regionRepository.findById(newRegion.getId()).isPresent();
            if(isUpdated){
                return CustomResponse.generate(HttpStatus.OK, "Data saved");
            }
        }else{
            regionRepository.save(region);
            Boolean isCreated = regionRepository.findById(region.getId()).isPresent();
            if(isCreated){
                return CustomResponse.generate(HttpStatus.OK, "Data saved");
            } 
        }return CustomResponse.generate(HttpStatus.OK, "Data failed to save");
    }

    @DeleteMapping("region/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        regionRepository.deleteById(id);
        Boolean isDeleted = regionRepository.findById(id).isEmpty();
        if(isDeleted){
            return CustomResponse.generate(HttpStatus.OK, "Data deleted");
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to delete");
        }
        
    }

    @GetMapping("region/{id}")
    public ResponseEntity<Object> Get(@PathVariable(required = true) Integer id){
        regionRepository.findById(id);
        Boolean isExist = regionRepository.findById(id).isPresent();
        if(isExist){
            return CustomResponse.generate(HttpStatus.OK, "Data retrieved", regionRepository.findById(id));
        }else{
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data failed to retrieve");
        }
    }

    


}
