package com.example.library_project;

import com.example.library_project.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SecurityConfigTest {
    @Autowired
    HttpSecurity httpSecurity;
    @Autowired
    SecurityConfig securityConfig;

    @Test
    void filterChainIsNotNull_test() throws Exception{
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity);
        assertNotNull(filterChain);
    }

    @Test
    void passwordEncoderIsNotNull_test(){
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
    }

    @Test
    void passwordEncoderEncodesPassword_test(){
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String testString = "testString";
        String encodedString = encoder.encode(testString);
        assertNotEquals(testString, encodedString);
    }

    @Test
    void passwordEncoderEqualStringAndEncodedPassword_test(){
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String testString = "testString";
        String encodedString = encoder.encode(testString);
        assertTrue(encoder.matches(testString, encodedString));
    }
}
