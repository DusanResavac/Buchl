package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value= "SELECT * FROM book b WHERE " +
            "(:tag is null or :tag in (select tag_id from tags_books where book_id = b.id)) and " +
            "(:rating is null or :rating <= (select SUM(b.stars)/(select count(*) from review r where r.book_id = b.id)))",
            nativeQuery = true
    )
    List<Book> findAllByFilters(@Param("tag") @Nullable Long tag,
                                @Param("rating") @Nullable Long rating/*, Integer author, Integer releaseYearFrom, Integer releaseYearUntil, String search*/);
    @Query(
            value = "select * from book order by rand() limit 3",
            nativeQuery = true)
    List<Book> findTop3Favourites();
    Book findFirstByTitleIgnoreCase(String title);
    Book findFirstByTitleContainingIgnoreCase(String title);
}
