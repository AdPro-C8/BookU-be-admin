package id.ac.ui.cs.advprog.admin.repository;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import id.ac.ui.cs.advprog.admin.models.Book;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    class BookSpecifications {
        public static Specification<Book> authorIs(String author) {
            return (root, query, builder) -> {
                return builder.equal(root.get("author"), author);
            };
        }

        public static Specification<Book> titleIs(String title) {
            return (root, query, builder) -> {
                return builder.equal(root.get("title"), title);
            };
        }
    }
}