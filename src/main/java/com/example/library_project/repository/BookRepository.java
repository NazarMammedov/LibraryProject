package com.example.library_project.repository;

import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByUser(User user);
}
