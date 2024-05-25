package id.ac.ui.cs.advprog.admin.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import id.ac.ui.cs.advprog.admin.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.admin.models.Book;
import id.ac.ui.cs.advprog.admin.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) throws RuntimeException {
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    public void deleteById(UUID bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    public List<Book> findAll(Specification<Book> spec) {
        return bookRepository.findAll(spec);
    }

    public List<Book> findAll(Specification<Book> spec, Sort sort) {
        return bookRepository.findAll(spec, sort);
    }
}