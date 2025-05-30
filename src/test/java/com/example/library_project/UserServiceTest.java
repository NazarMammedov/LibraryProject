package com.example.library_project;

import com.example.library_project.model.User;
import com.example.library_project.repository.UserRepository;
import com.example.library_project.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@PropertySource("classpath:/resources/application-test.properties")
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before("")
    public void init() {
        userService = new UserService(userRepository);
    }

    @Test
    void loadUsersByUserName_test() {
        User user = new User("test", "test", "test");
        when(userService.findByEmail(any())).thenReturn(user);
        assertEquals(userService.loadUserByUsername("test").getUsername(), user.getEmail());
    }


    @Test
    void createUserSavesUser_test() {
        User user = new User();
        user.setBook(new ArrayList<>());
        user.setName("test");
        user.setPassword("test");
        user.setEmail("test");
        when(userRepository.save(any())).thenReturn(user);
        userService.createUser(user);

        assertNotNull(user);
    }

    @Test
    void createUserCallsOne_test() {
        User user = new User();
        user.setEmail("test");
        user.setName("test");
        user.setPassword("test");
        userService.createUser(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void findByEmailFindsUserByEmail_test() {
        User user = new User("test", "test", "test");
        when(userRepository.findByEmail(any())).thenReturn(user);
        assertNotNull(userService.findByEmail("test"));
    }

    @Test
    void findByEmailCallsOnce_test() {
        userService.findByEmail("test");
        verify(userRepository, times(1)).findByEmail(any());
    }


}
