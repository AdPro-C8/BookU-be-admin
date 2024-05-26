package id.ac.ui.cs.advprog.admin.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import id.ac.ui.cs.advprog.admin.dto.PatchBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookRequestDto;
import id.ac.ui.cs.advprog.admin.enums.BookServiceException;
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
    public String bookHost;

    private final RestClient restClient;
    private String book_url = "/book";
    private String book_url2 = "/book/";

    @Autowired
    public BookService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Book save(Book book) {
        try {
            return restClient.post()
                    .uri(bookHost + book_url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpStatusCodeException e) {
            throw new BookServiceException("Error saving book: " + e.getMessage(), e);
        }
    }

    //This method still needs work to do since it deletes then save the updated book, so the id is different (not intented)
    public void update(UUID bookId, PatchBookRequestDto dto) {
        try {
            restClient.patch()
                    .uri(bookHost + book_url2 + bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpStatusCodeException e) {
            throw new BookServiceException("Error saving book: " + e.getMessage(), e);
        }
    }

    public Optional<Book> findById(UUID bookId) {
        try {
            Book book = restClient.get()
                    .uri(bookHost + book_url2 + bookId)
                    .retrieve()
                    .body(Book.class);
            return Optional.ofNullable(book);
        } catch (HttpStatusCodeException e) {
            throw new BookServiceException("Error finding book by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(UUID bookId) {
        try {
            restClient.delete()
                    .uri(bookHost + book_url2 + bookId)
                    .retrieve();
        } catch (HttpStatusCodeException e) {
            throw new BookServiceException("Error deleting book: " + e.getMessage(), e);
        }
    }

    public List<Book> findAll(
        Optional<String> author, Optional<String> title,
        Optional<String> sortBy, Optional<String> orderBy)
    throws RestClientException
    {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(bookHost + book_url);

        if (author.isPresent()) {
            uriBuilder.queryParam("author", author);
        }
        if (title.isPresent()) {
            uriBuilder.queryParam("title", title);
        }
        if (sortBy.isPresent()) {
            uriBuilder.queryParam("sortBy", sortBy);
        }
        if (orderBy.isPresent()) {
            uriBuilder.queryParam("orderBy", orderBy);
        }

        Book[] books = restClient.get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .body(Book[].class);

        return Arrays.asList(books);
    }
}
