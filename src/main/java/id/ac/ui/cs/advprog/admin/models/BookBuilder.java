package id.ac.ui.cs.advprog.admin.models;

import id.ac.ui.cs.advprog.admin.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class BookBuilder {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private Date tanggalTerbit;
    private String ISBN;
    private int jumlahHalaman;
    private String fotoCover;
    private int jumlahBeli;

    public BookBuilder id (String id) {
        if (id == null ||id.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.id =id;
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
            throw new IllegalArgumentException("publisher cannot be empty");
        }
        this.publisher = publisher;
        return this;
    }

    public BookBuilder price(int price) {
        if (price == 0) {
            throw new IllegalArgumentException("Price cannot be zero");
        }
        this.price = price;
        return this;
    }

    public BookBuilder tanggalTerbit(Date tanggalTerbit) {
        if (tanggalTerbit == null) {
            throw new IllegalArgumentException("Tanggal terbit cannot be empty");
        }
        this.tanggalTerbit = tanggalTerbit;
        return this;
    }
    public BookBuilder ISBN(String ISBN) {
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        this.ISBN = ISBN;
        return this;
    }
    public BookBuilder jumlahHalaman(int jumlahHalaman) {
        if (jumlahHalaman == 0) {
            throw new IllegalArgumentException("jumlahHalaman cannot be empty");
        }
        this.jumlahHalaman = jumlahHalaman;
        return this;
    }
    public BookBuilder fotoCover (String fotoCover ) {
        if (fotoCover  == null || fotoCover .isEmpty()) {
            throw new IllegalArgumentException("fotoCover  cannot be empty");
        }
        this.fotoCover  = fotoCover ;
        return this;
    }
    public BookBuilder jumlahBeli (int jumlahBeli){
        this.jumlahBeli = jumlahBeli;
        return this;
    }

    public Book build() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setTanggalTerbit(tanggalTerbit);
        book.setISBN(ISBN);
        book.setJumlahHalaman(jumlahHalaman);
        book.setFotoCover(fotoCover);
        book.setJumlahBeli(0);

        return book;
    }
}
