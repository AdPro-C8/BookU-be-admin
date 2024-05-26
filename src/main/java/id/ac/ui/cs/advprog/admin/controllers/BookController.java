package id.ac.ui.cs.advprog.admin.controllers;

import id.ac.ui.cs.advprog.admin.dto.PatchBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookResponseDto;
import id.ac.ui.cs.advprog.admin.models.Book;
import id.ac.ui.cs.advprog.admin.services.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    @Autowired
    private BookService bookService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Book>> getAllBooks(
            @RequestParam Optional<String> author, @RequestParam Optional<String> title,
            @RequestParam Optional<String> sortBy, @RequestParam Optional<String> orderBy)
    {
        List<Book> books;
        try {
            books = bookService.findAll(author, title, sortBy, orderBy);
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBooks(@PathVariable UUID id) {
        ResponseEntity responseEntity = null;
        try {
            Optional<Book> book = bookService.findById(id);
            responseEntity = ResponseEntity.ok(book);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("")
    public ResponseEntity<PostBookResponseDto> createBook(@RequestBody PostBookRequestDto bookDto, @RequestHeader ("Authorization") String authHeader) {
        Book newBook = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .price(bookDto.getPrice())
                .publishDate(bookDto.getPublishDate())
                .isbn(bookDto.getIsbn())
                .pageCount(bookDto.getPageCount())
                .photoUrl(bookDto.getPhotoUrl())
                .category(bookDto.getCategory())
                .build();

        try {
            newBook = bookService.save(newBook, authHeader);
        } catch (DataIntegrityViolationException exception) {
            logger.error("Data integrity violation: {}", exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException exception) {
            logger.error("Unexpected error: {}", exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        UUID bookId = newBook.getId();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PostBookResponseDto(bookId));
    }

    @PatchMapping("/{bookId}")
    ResponseEntity<Void> updateBookById(
            @PathVariable UUID bookId,
            @RequestBody PatchBookRequestDto bookDto)
    {
        bookService.update(bookId, bookDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteBookById(@PathVariable UUID bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (book.get().getDownloadCount() > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        bookService.deleteById(bookId);
        return ResponseEntity.ok().build();
    }
}