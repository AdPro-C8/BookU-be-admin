package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublisher("Test Publisher");
        book.setPrice(100);
        book.setPublishDate(Date.valueOf(LocalDate.now()));
        book.setIsbn("1234567890123");
        book.setPageCount(200);
        book.setPhotoUrl("http://example.com/photo.jpg");
        book.setCategory("Test Category");
        book.setDownloadCount(10);
    }

    @Test
    public void testBookGettersAndSetters() {
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("Test Publisher", book.getPublisher());
        assertEquals(100, book.getPrice());
        assertEquals(Date.valueOf(LocalDate.now()), book.getPublishDate());
        assertEquals("123-4567890123", book.getIsbn());
        assertEquals(200, book.getPageCount());
        assertEquals("http://example.com/photo.jpg", book.getPhotoUrl());
        assertEquals("Test Category", book.getCategory());
        assertEquals(10, book.getDownloadCount());
    }

    @Test
    public void testBookBuilder() {
        Book bookFromBuilder = Book.builder()
                .id(UUID.randomUUID())
                .title("Builder Book")
                .author("Builder Author")
                .publisher("Builder Publisher")
                .price(150)
                .publishDate(Date.valueOf(LocalDate.now().minusDays(1)))
                .isbn("987-6543210987")
                .pageCount(300)
                .photoUrl("http://example.com/builder-photo.jpg")
                .category("Builder Category")
                .downloadCount(20)
                .build();

        assertEquals("Builder Book", bookFromBuilder.getTitle());
        assertEquals("Builder Author", bookFromBuilder.getAuthor());
        assertEquals("Builder Publisher", bookFromBuilder.getPublisher());
        assertEquals(150, bookFromBuilder.getPrice());
        assertEquals(Date.valueOf(LocalDate.now().minusDays(1)), bookFromBuilder.getPublishDate());
        assertEquals("987-6543210987", bookFromBuilder.getIsbn());
        assertEquals(300, bookFromBuilder.getPageCount());
        assertEquals("http://example.com/builder-photo.jpg", bookFromBuilder.getPhotoUrl());
        assertEquals("Builder Category", bookFromBuilder.getCategory());
        assertEquals(20, bookFromBuilder.getDownloadCount());
    }

    @Test
    public void testBookInvalidPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            book.setPrice(-1);
        });
        assertEquals("price should be >= 0", exception.getMessage());
    }

    @Test
    public void testBookInvalidPageCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            book.setPageCount(-1);
        });
        assertEquals("pageCount should be >= 0", exception.getMessage());
    }

    @Test
    public void testBookInvalidDownloadCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            book.setDownloadCount(-1);
        });
        assertEquals("downloadCount should be >= 0", exception.getMessage());
    }
}
