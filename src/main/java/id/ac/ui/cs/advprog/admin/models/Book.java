package id.ac.ui.cs.advprog.admin.models;

import id.ac.ui.cs.advprog.admin.models.BookBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author"})
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    private UUID id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(nullable = false)
    @Getter
    @Setter
    private String author;

    @Column(nullable = false)
    @Getter
    @Setter
    private String publisher;

    @Column(nullable = false)
    @Getter
    @Setter
    private int price;

    @Column(nullable = false)
    @Getter
    @Setter
    private Date tanggalTerbit;

    @Column(nullable = false)
    @Getter
    @Setter
    private String ISBN;

    @Column(nullable = false)
    @Getter
    @Setter
    private int jumlahHalaman;

    @Column(nullable = false)
    @Getter
    @Setter
    private String fotoCover;

    @Column(nullable = false)
    @Getter
    @Setter
    private int jumlahBeli;
    public static BookBuilder builder() {
        return new BookBuilder();
    }
}

