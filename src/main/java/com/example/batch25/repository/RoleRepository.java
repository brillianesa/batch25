package com.example.batch25.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
