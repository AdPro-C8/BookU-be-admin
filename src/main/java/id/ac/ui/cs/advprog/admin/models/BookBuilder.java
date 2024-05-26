package id.ac.ui.cs.advprog.admin.models;

import java.sql.Date;
import java.util.UUID;

public class BookBuilder {
    private UUID id;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private Date publishDate;
    private String isbn;
    private int pageCount;
    private String photoUrl;
    private String category;
    private int downloadCount;

    public BookBuilder id(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
        return this;
    }

    public BookBuilder title(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
        return this;
    }

    public BookBuilder author(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author = author;
        return this;
    }

    public BookBuilder publisher(String publisher) {
        if (publisher == null || publisher.isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be empty");
        }
        this.publisher = publisher;
        return this;
    }

    public BookBuilder price(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
        return this;
    }

    public BookBuilder publishDate(Date publishDate) {
        if (publishDate == null) {
            throw new IllegalArgumentException("Publish date cannot be null");
        }
        this.publishDate = publishDate;
        return this;
    }

    public BookBuilder isbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        this.isbn = isbn;
        return this;
    }

    public BookBuilder pageCount(int pageCount) {
        if (pageCount < 0) {
            throw new IllegalArgumentException("Page count cannot be negative");
        }
        this.pageCount = pageCount;
        return this;
    }

    public BookBuilder photoUrl(String photoUrl) {
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Photo URL cannot be empty");
        }
        this.photoUrl = photoUrl;
        return this;
    }

    public BookBuilder category(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category;
        return this;
    }

    public BookBuilder downloadCount(int downloadCount) {
        if (downloadCount < 0) {
            throw new IllegalArgumentException("Download count cannot be negative");
        }
        this.downloadCount = downloadCount;
        return this;
    }

    public Book build() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setPublishDate(publishDate);
        book.setIsbn(isbn);
        book.setPageCount(pageCount);
        book.setPhotoUrl(photoUrl);
        book.setCategory(category);
        book.setDownloadCount(downloadCount);


        return book;
    }
}
