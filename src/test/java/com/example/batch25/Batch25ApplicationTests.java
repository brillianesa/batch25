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
		region.setName("Waktu Indonesia Timur");
		Boolean expectedValue = true;
		//ACT
		regionRepository.save(region);
		Boolean actualValue = regionRepository.findById(region.getId()).isPresent();

		//ASSERT
		assertEquals(expectedValue, actualValue);
	}
	@Test
	public void update(){
		Region region = new Region();
		region.setName("Waktu Indonesia Timur");
		region.setId(1);
		Boolean expectedValue = true;

		regionRepository.save(region);
		Boolean actualValue;
		if(regionRepository.CountByName("Waktu Indonesia Timur") > 0 && regionRepository.findById(region.getId()).isPresent()){
			actualValue = true;
		}else{
			actualValue = false;
		}

		assertEquals(expectedValue, actualValue);
		}

	@Test
	public void delete(){
		Region region = new Region();
		region.setId(3);

		Boolean expectedValue = false;
		regionRepository.delete(region);

		Boolean actualValue = regionRepository.findById(region.getId()).isPresent();

		assertEquals(expectedValue, actualValue);
	}
}
