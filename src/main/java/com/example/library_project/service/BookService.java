package com.example.library_project.service;

import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import com.example.library_project.repository.BookRepository;
import com.example.library_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book loanBook(Long book_id, String email) {
        Book book = findBookById(book_id);
        if (book.getUser() == null) {
            User user = userRepository.findByEmail(email);
            book.setUser(user);
            book.setStatus(false);
            bookRepository.save(book);
            return book;
        } else {
            return null;
        }
    }

    public Book returnBook(Long book_id, Long user_id){
        Book book = bookRepository.findById(book_id).orElseThrow();
        if (book.getUser() == null || !Objects.equals(book.getUser().getId(), user_id)){
            return null;
        }else {
            book.setStatus(true);
            book.setUser(null);
            bookRepository.save(book);
            return book;
        }
    }

    public List<Book> findBooksByUser(User currentUser){
        return bookRepository.findBooksByUser(currentUser);
    }
}
