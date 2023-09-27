package com.example.batch25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.batch25.model.Region;
import com.example.batch25.repository.RegionRepository;

@SpringBootTest
class Batch25ApplicationTests {
	@Autowired
	private RegionRepository regionRepository;
	// @Test
	// void contextLoads() {


	// }

	@Test
	public void insert(){
		//ARRANGE
		Region region = new Region();
		region.setName("Waktu Indonesia Barat");
		Boolean expectedValue = true;
		//ACT
		regionRepository.save(region);
		Boolean actualValue = regionRepository.findById(region.getRegion_id()).isPresent();

		//ASSERT
		assertEquals(expectedValue, actualValue);
	}
	@Test
	public void update(){
		Region region = new Region();
		region.setName("Waktu Indonesia Timur");
		region.setRegion_id(1);
		Boolean expectedValue = true;

		regionRepository.save(region);
		// Boolean actualValue = regionRepository.findById(region.getRegion_id()).isPresent();
		Boolean testValue;
		if(regionRepository.CountByName("Waktu Indonesia Timur") > 0 && regionRepository.findById(region.getRegion_id()).isPresent()){
			testValue = true;
		}else{
			testValue = false;
		}

		assertEquals(expectedValue, testValue);
		}

	@Test
	public void delete(){
		Region region = new Region();
		region.setRegion_id(1);

		Boolean expectedValue = false;
		regionRepository.delete(region);

		Boolean actualValue = regionRepository.findById(region.getRegion_id()).isPresent();

		assertEquals(expectedValue, actualValue);
	}
}
