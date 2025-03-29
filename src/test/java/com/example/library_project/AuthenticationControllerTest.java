package com.example.library_project;

import com.example.library_project.model.User;
import com.example.library_project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @MockitoBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder encoder;

    @Test
    void loginPostRequestSuccessfulLoginStatus_test() throws Exception {
        User user = new User();
        user.setEmail("test");
        user.setPassword(encoder.encode("test"));
        when(userService.loadUserByUsername(any())).thenReturn(user);
        mockMvc.perform(post("/login").param("username", "test").param("password", "test")).andExpectAll(status().is3xxRedirection(), redirectedUrl("/"));
    }

    @Test
    void loginPostRequestUnsuccessfulLoginStatus_test() throws Exception {
        User user = new User();
        user.setEmail("test");
        user.setPassword(encoder.encode("test"));
        when(userService.loadUserByUsername("test")).thenReturn(user);
        mockMvc.perform(post("/login").param("username", "test").param("password", "testiui")).andExpect(redirectedUrl("/login/error"));

    }

    @Test
    @WithAnonymousUser
    void loginGetRequestWithAnonymousUserStatus_test() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void loginGetRequestWithAuthenticatedUserStatus_test() throws Exception {
        mockMvc.perform(get("/login")).andExpectAll(status().isForbidden(), forwardedUrl("/"));
    }

    @Test
    @WithAnonymousUser
    void registerUserPostRequestWithAnonymousUserStatus_test() throws Exception {
        User user = new User("test", "test", "test");
        when(userService.createUser(any())).thenReturn(user);
        mockMvc.perform(post("/register-user").param("name", "test").param("email", "test").param("password", "test")).andExpectAll(status().is3xxRedirection(), redirectedUrl("/login"));
        verify(userService, times(1)).createUser(any());

    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void registerUserPostRequestWithAuthenticatedUserStatus_test() throws Exception {
        User user = new User("test", "test", "test");
        when(userService.createUser(any())).thenReturn(user);
        mockMvc.perform(post("/register-user").param("name", "test").param("email", "test").param("password", "test")).andExpectAll(status().isForbidden(), forwardedUrl("/"));
    }

    @Test
    void registerUserPostRequestUserAlreadyExistsStatus_test() throws Exception {
        User user = new User("test", "test", "test");
        when(userService.createUser(any())).thenReturn(user);
        when(userService.findByEmail(any())).thenReturn(user);
        mockMvc.perform(post("/register-user").param("name", "test").param("email", "test").param("password", "test")).andExpectAll(status().isOk(), model().attribute("error", "E-posten "+user.getEmail()+" blir brukt av en annen bruker"));
    }

    @Test
    @WithAnonymousUser
    void registrationGetRequestWithAnonymousUserStatus_test() throws Exception {
        mockMvc.perform(get("/registration")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void registrationGetRequestWithAuthenticatedUserStatus_test() throws Exception {
        mockMvc.perform(get("/registration")).andExpectAll(status().isForbidden(), forwardedUrl("/"));
    }
}
