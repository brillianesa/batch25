package com.example.batch25.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{
    @Query(value = "SELECT COUNT(*) FROM tb_m_region WHERE NAME = ?1",nativeQuery = true )
    public Integer CountByName(String name);
}
