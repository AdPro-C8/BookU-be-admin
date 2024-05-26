package id.ac.ui.cs.advprog.admin.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import id.ac.ui.cs.advprog.admin.models.Book;

@Service
public class BookService {

    @Value("${api.book-host}")
    private String bookHost;

    private final RestClient restClient;

    @Autowired
    public BookService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Book save(Book book) {
        try {
            return restClient.post()
                    .uri(bookHost + "/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    public Book update(Book book) {
        try {
            return restClient.patch()
                    .uri(bookHost + "/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    public Optional<Book> findById(UUID bookId) {
        try {
            Book book = restClient.get()
                    .uri(bookHost + "/book/" + bookId)
                    .retrieve()
                    .body(Book.class);
            return Optional.ofNullable(book);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error finding book by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(UUID bookId) {
        try {
            restClient.delete()
                    .uri(bookHost + "/book/" + bookId)
                    .retrieve();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error deleting book: " + e.getMessage(), e);
        }
    }

    public List<Book> findAll(
        Optional<String> author, Optional<String> title,
        Optional<String> sortBy, Optional<String> orderBy)
    throws RestClientException
    {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(bookHost + "/book");

        if (author != null) {
            uriBuilder.queryParam("author", author);
        }
        if (title != null) {
            uriBuilder.queryParam("title", title);
        }
        if (sortBy != null) {
            uriBuilder.queryParam("sortBy", sortBy);
        }
        if (orderBy != null) {
            uriBuilder.queryParam("orderBy", orderBy);
        }

        Book[] books = restClient.get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .body(Book[].class);

        return Arrays.asList(books);
    }
}
