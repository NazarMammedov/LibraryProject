package com.example.library_project;

import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import com.example.library_project.service.BookService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.library_project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    BookService bookService;
    @MockitoBean
    UserService userService;

    @Test
    void bookInfoPageGetRequestStatus_test() throws Exception {
        Book book = new Book();
        book.setId(1L);
        when(bookService.findBookById(any())).thenReturn(book);
        mockMvc.perform(get("/book/info/{id}", "1")).andExpect(status().isOk());
        verify(bookService, times(1)).findBookById(any());

    }
    @Test
    @WithAnonymousUser
    void loanBookPostRequestWithAnonymousUserStatus_test() throws Exception {
        Book book = new Book();
        book.setId(1L);
        when(bookService.findBookById(any())).thenReturn(book);
        mockMvc.perform(get("/loan-book/{id}", "1")).andExpectAll(status().is3xxRedirection(), redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithMockUser(username = "test", password = "test")
    void loanBookPostRequestWithAuthenticatedUserStatus_test() throws Exception {
        Book book = new Book();
        User user = new User("test", "test","test");
        book.setId(1L);
        when(bookService.findBookById(any())).thenReturn(book);
        when(userService.findByEmail(any())).thenReturn(user);
        mockMvc.perform(get("/loan-book/{id}", "1")).andExpectAll(status().is3xxRedirection(), redirectedUrl("/book/info/1"));
        verify(bookService, times(1)).loanBook(any(), any());
    }

    @Test
    void myBooksGetRequestWithAnonymousUserStatus_test() throws Exception {
        mockMvc.perform(get("/my-books")).andExpectAll(status().is3xxRedirection(), redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithMockUser(username = "test", password = "test")
    void myBooksGetRequestWithAuthenticatedUserStatus_test() throws Exception {
        List<Book> books = new ArrayList<>();
        when(bookService.findBooksByUser(any())).thenReturn(books);
        mockMvc.perform(get("/my-books")).andExpectAll(status().isOk());
        verify(bookService, times(1)).findBooksByUser(any());
    }

    @Test
    @WithAnonymousUser
    void returnBookGetRequestWithAnonymousUserStatus_test() throws Exception{
        mockMvc.perform(get("/return-book/{book_id}","1")).andExpectAll(status().is3xxRedirection(), redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithMockUser(username = "test", password = "test")
    void returnBookGetRequestWithAuthenticatedUserStatus_test() throws Exception{
        Book book = new Book();
        book.setId(1L);
        User user = new User();
        user.setId(1L);
        book.setUser(user);
        when(userService.findByEmail(any())).thenReturn(user);
        when(bookService.findBookById(any())).thenReturn(book);
        when(bookService.returnBook(any(), any())).thenReturn(book);
        mockMvc.perform(get("/return-book/{book_id}","1")).andExpectAll(status().is3xxRedirection(), redirectedUrl("/book/info/1"));
        verify(bookService, times(1)).returnBook(any(), any());
    }
}
