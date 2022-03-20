package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findFirstByTitleIgnoreCase(String title);
    Book findFirstByTitleContainingIgnoreCase(String title);
}
