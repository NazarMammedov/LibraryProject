package com.example.library_project.controller;

import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import com.example.library_project.service.BookService;
import com.example.library_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/book/info/{id}")
    public String bookInfoPage(@PathVariable("id") Long book_id, Model model) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Book book = bookService.findBookById(book_id);

        User currentUser = userService.findByEmail(currentUserEmail);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("book", book);
        return "bookInfo";
    }

    @RequestMapping(value = "/loan-book/{book_id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String loanBook(@PathVariable("book_id") Long book_id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        Book currentBook = bookService.findBookById(book_id);
        if (bookService.loanBook(currentBook.getId(), currentUserEmail) == null) {
            redirectAttributes.addFlashAttribute("error", "Denne boken er allerede utlånt av " + currentBook.getUser().getName());
        }
        bookService.loanBook(book_id, currentUser.getEmail());
        return "redirect:/book/info/" + book_id;
    }

    @RequestMapping(value = "/return-book/{book_id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String returnBook(@PathVariable("book_id") Long book_id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        Book currentBook = bookService.findBookById(book_id);
        if (bookService.returnBook(currentBook.getId(), currentUser.getId()) == null) {
            redirectAttributes.addFlashAttribute("error", "Du har ikke lånt den boken");
        } else {
            bookService.returnBook(book_id, currentUser.getId());
            redirectAttributes.addFlashAttribute("success", "Du har levert denne boken!");
        }
        return "redirect:/book/info/" + currentBook.getId();
    }


    @GetMapping("/my-books")
    public String myBooks(Model model) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        List<Book> books = bookService.findBooksByUser(currentUser);
        model.addAttribute("books", books);
        model.addAttribute("currentUser", currentUser);
        return "myBooks";
    }
}
