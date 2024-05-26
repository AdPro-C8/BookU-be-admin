package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.models.Book;
import id.ac.ui.cs.advprog.admin.enums.BookServiceException;
import id.ac.ui.cs.advprog.admin.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private BookService bookService;

    @Value("${api.book-host}")
    private String bookHost = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(restClient);
        bookService.bookHost = bookHost;
    }

    @Test
    public void testSave() {
        Book book = new Book();
        when(restClient.post().uri(anyString()).contentType(any(MediaType.class)).body(any(Book.class))
                .retrieve().body(Book.class)).thenReturn(book);

        Book savedBook = bookService.save(book);

        assertNotNull(savedBook);
        verify(restClient, times(1)).post().uri(anyString()).contentType(any(MediaType.class)).body(any(Book.class));
    }

    @Test
    public void testUpdate() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setId(bookId);

        when(restClient.get().uri(anyString()).retrieve().body(Book.class)).thenReturn(book);
        when(restClient.post().uri(anyString()).contentType(any(MediaType.class)).body(any(Book.class))
                .retrieve().body(Book.class)).thenReturn(book);

        Book updatedBook = bookService.update(book);

        assertNotNull(updatedBook);
        verify(restClient, times(1)).get().uri(anyString()).retrieve();
        verify(restClient, times(1)).delete().uri(anyString()).retrieve();
        verify(restClient, times(1)).post().uri(anyString()).contentType(any(MediaType.class)).body(any(Book.class));
    }

    @Test
    public void testFindById() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setId(bookId);

        when(restClient.get().uri(anyString()).retrieve().body(Book.class)).thenReturn(book);

        Optional<Book> foundBook = bookService.findById(bookId);

        assertTrue(foundBook.isPresent());
        assertEquals(bookId, foundBook.get().getId());
        verify(restClient, times(1)).get().uri(anyString()).retrieve();
    }

    @Test
    public void testDeleteById() {
        UUID bookId = UUID.randomUUID();

        doNothing().when(restClient.delete().uri(anyString()).retrieve());

        bookService.deleteById(bookId);

        verify(restClient, times(1)).delete().uri(anyString()).retrieve();
    }

    @Test
    public void testFindAll() {
        Book book1 = new Book();
        Book book2 = new Book();
        Book[] booksArray = {book1, book2};

        when(restClient.get().uri(anyString()).retrieve().body(Book[].class)).thenReturn(booksArray);

        List<Book> books = bookService.findAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotNull(books);
        assertEquals(2, books.size());
        verify(restClient, times(1)).get().uri(anyString()).retrieve();
    }
}
