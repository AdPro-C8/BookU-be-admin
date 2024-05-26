package id.ac.ui.cs.advprog.admin.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
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

    public List<Book> findAll() {
        try {
            Book[] books = restClient.get()
                    .uri(bookHost + "/book")
                    .retrieve()
                    .body(Book[].class);
            return Arrays.asList(books);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error finding all books: " + e.getMessage(), e);
        }
    }

    public List<Book> findAll(Sort sort) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(bookHost + "/book");
        sort.forEach(order -> uriBuilder.queryParam("sort", order.getProperty() + "," + order.getDirection().name().toLowerCase()));

        try {
            Book[] books = restClient.get()
                    .uri(uriBuilder.toUriString())
                    .retrieve()
                    .body(Book[].class);
            return Arrays.asList(books);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error finding all books with sort: " + e.getMessage(), e);
        }
    }

    public List<Book> findAll(Specification<Book> spec) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(bookHost + "/book");

        if (spec != null) {
            if (spec.toString().contains("author")) {
                String author = extractValueFromSpec(spec, "author");
                if (author != null) {
                    uriBuilder.queryParam("author", author);
                }
            }
            if (spec.toString().contains("title")) {
                String title = extractValueFromSpec(spec, "title");
                if (title != null) {
                    uriBuilder.queryParam("title", title);
                }
            }
        }

        try {
            Book[] books = restClient.get()
                    .uri(uriBuilder.toUriString())
                    .retrieve()
                    .body(Book[].class);
            return Arrays.asList(books);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error finding all books with spec: " + e.getMessage(), e);
        }
    }

    public List<Book> findAll(Specification<Book> spec, Sort sort) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(bookHost + "/book");

        if (spec != null) {
            if (spec.toString().contains("author")) {
                String author = extractValueFromSpec(spec, "author");
                if (author != null) {
                    uriBuilder.queryParam("author", author);
                }
            }
            if (spec.toString().contains("title")) {
                String title = extractValueFromSpec(spec, "title");
                if (title != null) {
                    uriBuilder.queryParam("title", title);
                }
            }
        }

        sort.forEach(order -> uriBuilder.queryParam("sort", order.getProperty() + "," + order.getDirection().name().toLowerCase()));

        try {
            Book[] books = restClient.get()
                    .uri(uriBuilder.toUriString())
                    .retrieve()
                    .body(Book[].class);
            return Arrays.asList(books);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error finding all books with spec and sort: " + e.getMessage(), e);
        }
    }

    private String extractValueFromSpec(Specification<Book> spec, String key) {
        String specString = spec.toString();
        int start = specString.indexOf(key + "=");
        if (start != -1) {
            int end = specString.indexOf(",", start);
            if (end == -1) {
                end = specString.length();
            }
            return specString.substring(start + key.length() + 1, end).trim();
        }
        return null;
    }
}
