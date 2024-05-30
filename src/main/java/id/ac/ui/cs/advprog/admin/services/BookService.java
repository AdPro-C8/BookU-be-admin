package id.ac.ui.cs.advprog.admin.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import id.ac.ui.cs.advprog.admin.dto.GetBooksByIdRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PatchBookRequestDto;
import id.ac.ui.cs.advprog.admin.dto.PostBookRequestDto;

import id.ac.ui.cs.advprog.admin.dto.PostBookResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import id.ac.ui.cs.advprog.admin.models.Book;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Value("${api.book-host}")
    public String bookHost;

    private final RestClient restClient;
    private String book_url = "/book";
    private String book_url2 = "/book/";

    @Autowired
    public BookService(RestClient restClient) {
        this.restClient = restClient;
    }

    public PostBookResponseDto save(PostBookRequestDto bookDto)
            throws HttpStatusCodeException
    {
        try {
            return restClient.post()
                    .uri(bookHost + book_url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookDto)
                    .retrieve()
                    .body(PostBookResponseDto.class);
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void updateById(UUID bookId, PatchBookRequestDto dto)
            throws HttpStatusCodeException
    {
        try {
            restClient.patch()
                    .uri(bookHost + book_url2 + bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve();
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public Book findById(UUID bookId) throws HttpStatusCodeException {
        try {
            return restClient.get()
                    .uri(bookHost + book_url2 + bookId)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteById(UUID bookId) throws HttpStatusCodeException {
        try {
            Book book = findById(bookId);
            if (book.getDownloadCount() == 0) {
                restClient.delete()
                        .uri(bookHost + book_url2 + bookId)
                        .retrieve();
            } else {
                logger.warn("Attempted to delete a book with downloads > 0: {}", bookId);
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot delete a book with downloads > 0");
            }
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public List<Book> findAll(
            Optional<String> author, Optional<String> title,
            Optional<String> sortBy, Optional<String> orderBy)
            throws HttpStatusCodeException
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

        Book[] books;
        try {
            books = restClient.get()
                    .uri(uriBuilder.toUriString())
                    .retrieve()
                    .body(Book[].class);
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }

        return List.of(books);
    }

    public List<Book> findAllById(GetBooksByIdRequestDto dto) throws HttpStatusCodeException {
        Book[] books;
        try {
            books = restClient.post()
                    .uri(bookHost + book_url + "/get-multiple")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve()
                    .body(Book[].class);
        } catch (HttpStatusCodeException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }

        return List.of(books);
    }
}
