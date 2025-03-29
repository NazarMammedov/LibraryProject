package com.example.library_project.controller;

import com.example.library_project.config.SecurityConfig;
import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import com.example.library_project.service.BookService;
import com.example.library_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final BookService bookService;
    private final UserService userService;
    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("books", bookService.getEveryBook());
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        List<Book> books = bookService.getEveryBook();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("books", books);
        return "mainPage";
    }
}
