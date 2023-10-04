package com.example.batch25.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.batch25.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "SELECT * FROM tb_m_user WHERE email = ?1 AND password = ?2", nativeQuery = true)
    public User login(String email, String password);

    @Query(value = "SELECT * FROM tb_m_user WHERE email = ?1", nativeQuery = true)
    public User findByEmail(String email);

    @Query(value = "SELECT new com.example.batch25.config.MyUserDetails(u.email, u.password, r.name) FROM User u JOIN u.role r WHERE u.email = ?1 ")
    public UserDetails loginNext(String email);
}
