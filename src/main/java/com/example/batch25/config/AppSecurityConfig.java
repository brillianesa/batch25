package com.example.batch25.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig{
    //Authentication
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // @Autowired
    // private UserDetailsService userDetails;

    // @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// 	// configure AuthenticationManager so that it knows from where to load
	// 	// user for matching credentials
	// 	// Use BCryptPasswordEncoder
	// 	auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
	// }

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
                                .antMatchers("/account/login").permitAll()
                                .antMatchers("/employee").permitAll()
                                .antMatchers("/employee/form").permitAll()
                                .antMatchers("/employee/save").permitAll()
                                .antMatchers("/account/authenticate").permitAll()
                                .antMatchers("/authenticate").permitAll()
                                .antMatchers("/account/changepassword").authenticated()
                                .antMatchers("/department/**").hasAuthority("Staff")
                                .antMatchers("/region/**").permitAll()
                                .anyRequest().permitAll() //ganti ke authenticated()
                                .and()
                                .formLogin()
                                .loginPage("/account/login")
                                .and()
                                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
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
