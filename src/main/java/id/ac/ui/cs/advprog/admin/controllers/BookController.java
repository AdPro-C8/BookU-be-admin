package id.ac.ui.cs.advprog.admin.controllers;

import id.ac.ui.cs.advprog.admin.dto.GetBooksByIdRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PatchBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookResponseDto;
import id.ac.ui.cs.advprog.admin.models.Book;
import id.ac.ui.cs.advprog.admin.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/book")
class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    ResponseEntity<List<Book>> getAllBooks(
            @RequestParam Optional<String> author, @RequestParam Optional<String> title,
            @RequestParam Optional<String> sortBy, @RequestParam Optional<String> orderBy)
    {
        List<Book> books;
        try {
            books = bookService.findAll(author, title, sortBy, orderBy);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book;
        try {
            book = bookService.findById(id);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping("/get-multiple")
    ResponseEntity<List<Book>> getMultipleBooksById(
            @RequestBody GetBooksByIdRequestDto dto)
    {
        List<Book> books;
        try {
            books = bookService.findAllById(dto);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        return ResponseEntity.ok(books);
    }

    @PostMapping("")
    ResponseEntity<PostBookResponseDto> createBook(@RequestBody PostBookRequestDto bookDto) {
        try {
            PostBookResponseDto responseDto = bookService.save(bookDto);
            return ResponseEntity.ok(responseDto);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

    @PatchMapping("/{bookId}")
    ResponseEntity<Void> updateBookById(
            @PathVariable UUID bookId,
            @RequestBody PatchBookRequestDto bookDto)
    {
        try {
            bookService.updateById(bookId, bookDto);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteBookById(@PathVariable UUID bookId) {
        try {
            bookService.deleteById(bookId);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        return ResponseEntity.ok().build();
    }
}