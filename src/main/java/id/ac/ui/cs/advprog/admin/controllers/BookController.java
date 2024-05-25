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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static id.ac.ui.cs.advprog.admin.repository.BookRepository.BookSpecifications.authorIs;
import static id.ac.ui.cs.advprog.admin.repository.BookRepository.BookSpecifications.titleIs;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    List<Book> getAllBooks(
            @RequestParam Optional<String> author, @RequestParam Optional<String> title,
            @RequestParam Optional<String> sortBy, @RequestParam Optional<String> orderBy)
    {
        Specification<Book> bookSpec = Specification.where(null);

        if (author.isPresent()) {
            bookSpec = bookSpec.and(authorIs(author.get()));
        }
        if (title.isPresent()) {
            bookSpec = bookSpec.and(titleIs(title.get()));
        }

        List<Book> bookList;

        if (sortBy.isPresent() && orderBy.isPresent()) {
            Sort sort = Sort.by(Direction.fromString(orderBy.get()), sortBy.get());
            bookList = bookService.findAll(bookSpec, sort);
        } else {
            bookList = bookService.findAll(bookSpec);
        }

        return bookList;
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
    public ResponseEntity<PostBookResponseDto> createBook(@RequestBody PostBookRequestDto bookDto) {
        Book newBook = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .price(bookDto.getPrice())
                .tanggalTerbit(bookDto.getTanggalTerbit())
                .ISBN(bookDto.getIsbn())
                .jumlahHalaman(bookDto.getJumlahHalaman())
                .fotoCover(bookDto.getFotoCover())
                .jumlahBeli(bookDto.getJumlahBeli())
                .build();

        try {
            newBook = bookService.save(newBook);
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
            @RequestBody PatchBookRequestDto bookDto) {
        Optional<Book> book = bookService.findById(bookId);

        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book someBook = book.get();

        Optional.ofNullable(bookDto.getPublisher())
                .ifPresent(publisher -> someBook.setPublisher(publisher));
        Optional.ofNullable(bookDto.getTanggalTerbit())
                .ifPresent(tanggalTerbit -> someBook.setTanggalTerbit(tanggalTerbit));
        Optional.ofNullable(bookDto.getIsbn())
                .ifPresent(ISBN -> someBook.setISBN(ISBN));
        Optional.ofNullable(bookDto.getJumlahHalaman())
                .ifPresent(jumlahHalaman -> someBook.setJumlahHalaman(jumlahHalaman));
        Optional.ofNullable(bookDto.getFotoCover())
                .ifPresent(photoUrl -> someBook.setFotoCover(photoUrl));

        bookService.save(someBook);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteBookById(@PathVariable UUID bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (book.get().getJumlahBeli() > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        bookService.deleteById(bookId);
        return ResponseEntity.ok().build();
    }
}