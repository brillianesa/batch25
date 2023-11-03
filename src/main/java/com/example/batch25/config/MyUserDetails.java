package com.example.batch25.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.batch25.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetails, UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    private String email;
    private String password;
    private String fullname;
    private GrantedAuthority grantedAuthority;

    public MyUserDetails(){
        super();
    }

    public MyUserDetails(String email, String password, String role, String fullname){
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.grantedAuthority = new SimpleGrantedAuthority(role);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loginNext(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
       return password;
       }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFullname(){
        return fullname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    }
}
