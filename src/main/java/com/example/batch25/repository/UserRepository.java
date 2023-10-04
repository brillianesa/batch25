package com.example.batch25.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "SELECT * FROM tb_m_user WHERE email = ?1 AND password = ?2", nativeQuery = true)
    public User login(String email, String password);

    @Query(value = "SELECT * FROM tb_m_user WHERE email = ?1", nativeQuery = true)
    public User findByEmail(String email);
}
