package com.example.batch25.controller.restAPI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch25.model.Region;
import com.example.batch25.repository.RegionRepository;

@RestController
@RequestMapping("api")
public class RegionRestController {
    @Autowired
    private RegionRepository regionRepository;


    @GetMapping("region")
    public List<Region> Get(){
        return regionRepository.findAll();
    }

    @PostMapping(value = {"region", "region/{id}"})
    public Boolean save(@RequestBody Region region, @PathVariable(required = false) Integer id){
        if (id != null){
            Region newRegion  = regionRepository.findById(id).orElse(null);
            newRegion.setId(id);
            newRegion.setName(region.getName());
            regionRepository.save(newRegion);
            return regionRepository.CountByName(newRegion.getName()) > 0 && regionRepository.findById(newRegion.getId()).isPresent();
        }else{
            regionRepository.save(region);
            return regionRepository.findById(region.getId()).isPresent();
        }
    }

    @DeleteMapping("region/{id}")
    public Boolean delete(@PathVariable(required = true) Integer id){
        regionRepository.deleteById(id);
        return regionRepository.findById(id).isEmpty();
    }

    @GetMapping("region/{id}")
    public Boolean Get(@PathVariable(required = true) Integer id){
        regionRepository.findById(id);
        return regionRepository.findById(id).isPresent();
    }

    


}
