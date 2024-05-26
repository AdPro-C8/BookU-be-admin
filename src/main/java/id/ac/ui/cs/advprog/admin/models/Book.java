package id.ac.ui.cs.advprog.admin.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author"})
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    @Check(constraints = "price >= 0")
    private int price;

    @Column(nullable = false)
    private Date publishDate;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    @Check(constraints = "page_count >= 0")
    private int pageCount;

    @Column(nullable = false)
    private String photoUrl;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Check(constraints = "download_count >= 0")
    private int downloadCount;

    public static BookBuilder builder() {
        return new BookBuilder();
    }
}
