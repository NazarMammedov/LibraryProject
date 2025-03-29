package com.example.library_project;

import com.example.library_project.model.Book;
import com.example.library_project.model.User;
import com.example.library_project.repository.BookRepository;
import com.example.library_project.repository.UserRepository;
import com.example.library_project.service.BookService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@PropertySource("classpath:/resources/application-test.properties")
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    BookService bookService;

    @Before("")
    public void init(){
        bookService = new BookService(bookRepository, userRepository);
    }

    @Test
    void getEveryBookFindAllBooks_test(){
        Book book = new Book();
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book);
        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);
        assertEquals(bookService.getEveryBook().size(), 3);
    }

    @Test
    void getEveryBookCallsOnce_test(){
        bookService.getEveryBook();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findBookByIdGetBookById_test(){
        Book book = new Book(1L,"test",true, "test", "test" ,"test", null, new User());
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertNotNull(bookService.findBookById(1L));
    }

    @Test
    void findBookByIdCallsOnce_test(){
        Book book = new Book();
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        bookService.findBookById(1L);
        verify(bookRepository, times(1)).findById(any());
    }

    @Test
    void loanBookSaveBookData_test(){
        Book book = new Book(1L,"test",true, "test", "test" ,"test", null, null);
        User user = new User("test", "test", "test");
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertNotNull(bookService.loanBook(book.getId(), user.getEmail()));

    }
    @Test
    void loanBookSaveBookDataWhenBookIsLoaned_test(){
        User user = new User("test", "test", "test");
        Book book = new Book(1L,"test", true, "test", "test" ,"test", null, user);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertNull(bookService.loanBook(book.getId(), user.getEmail()));

    }

    @Test
    void loanBookCallsOnce_test(){
        User user = new User("test", "test", "test");
        Book book = new Book(1L,"test",true, "test", "test" ,"test", null, null);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        bookService.loanBook(1L, "test");
        verify(bookRepository, times(1)).save(any());
        verify(bookRepository, times(1)).findById(any());
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void findBooksByUserGetBooksByUser_test(){
        List<Book> userBooks = new ArrayList<>();
        User user = new User();
        userBooks.add(new Book());
        userBooks.add(new Book());
        userBooks.add(new Book());
        when(bookRepository.findBooksByUser(any())).thenReturn(userBooks);
        assertEquals(bookService.findBooksByUser(user).size(), 3);
    }

    @Test
    void returnBookSavesData_test(){
        Book book = new Book();
        book.setId(1L);
        User user = new User();
        user.setId(1L);
        book.setUser(user);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertNotNull(bookService.returnBook(1L, 1L));
    }

    @Test
    void returnBookReturnBookWhenBookIsOwnedByAnotherUser_test(){
        Book book = new Book();
        book.setId(1L);
        User user = new User();
        user.setId(1L);
        book.setUser(user);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertNull(bookService.returnBook(1L, 2L));
    }
    @Test
    void findBooksByUserCallsOnce_test(){
        User user = new User();
        bookService.findBooksByUser(user);
        verify(bookRepository, times(1)).findBooksByUser(any());
    }

}
