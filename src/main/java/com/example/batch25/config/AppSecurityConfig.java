package com.example.batch25.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfig {
    //Authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Authorization
    // Staff -> bisa buka department
    // Admin -> bisa buka Region
    // Non login -> Login, Register, Forgot password
    // Login required -> change password

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth
                                .antMatchers("/account/login/").permitAll()
                                .antMatchers("account/register").permitAll()
                                .antMatchers("account/changepassword").authenticated()
                                .antMatchers("/department/**").hasAuthority("Staff")
                                .antMatchers("/region/**").hasAuthority("Manager")
                                .anyRequest().authenticated()
                                .and()
                                .formLogin()
                                .loginPage("/account/login")
                                .and()
                                .logout(); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return httpSecurity.build();
    }

    //encrypt
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
}
