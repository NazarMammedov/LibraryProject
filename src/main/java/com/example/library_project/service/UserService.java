package com.example.library_project.service;

import com.example.library_project.model.User;
import com.example.library_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        String user_email = user.getUsername();
        String user_password = user.getPassword();
        log.info("finding user with email {}", email);
        return new org.springframework.security.core.userdetails.User(user_email, user_password, user.getAuthorities());
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createUser(User newUser) {
            User user = new User();
            user.setPassword(encoder.encode(newUser.getPassword()));
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            log.info("user created {}", user.getId());
            return userRepository.save(user);
    }
}
