package com.example.library_project.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/registration", "/register-user", "/login/error").anonymous().requestMatchers( "/", "/book/info/{id}", "/scan-QR-code").permitAll().requestMatchers("/my-books", "/loan-book/{id}", "/return-book/{id}").authenticated().anyRequest().permitAll())
                .formLogin(login -> login.loginPage("/login").failureUrl("/login/error").defaultSuccessUrl("/"))
                .exceptionHandling(exception -> exception.accessDeniedPage("/"))
                .rememberMe(rememberMe -> rememberMe.alwaysRemember(true));
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
