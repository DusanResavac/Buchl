package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Get the book which has received the most positive average rating in the last 6 months
    @Query(value = "select b.* from book b " +
            "join review r on b.id = r.book_id " +
            "where r.date >= CURDATE() - interval 6 month or r.date is null " +
            "group by b.id " +
            "order by sum(r.rating) / count(r.rating) desc " +
            "limit 1",
            nativeQuery = true)
    Optional<Book> findCurrentlyMostPopularBook();

    // Get the book which has received the most non-3-star ratings
    /*
    This query would be more accurate, since it ignores books that are either rated overwhelmingly positively or very poorly
    select b.*
    from book b
             join review r on b.id = r.book_id
    where r.rating != 3
      and r.title is not null
      and r.date is not null
      and r.user is not null
      and 2 <= (select count(*)
                from book b2
                         join review r2 on b2.id = r2.book_id
                where r2.rating > 3
                  and b2.id = b.id)
      and 2 <= (select count(*)
                from book b2
                         join review r2 on b2.id = r2.book_id
                where r2.rating < 3
                  and b2.id = b.id)
    group by b.id
    order by count(*) desc limit 1
     */
    @Query(value = "select b.* from book b " +
            "join review r on b.id = r.book_id " +
            "where r.rating != 3 and " +
            "   r.title is not null and " +
            "   r.user_id is not null " +
            "group by b.id " +
            "order by count(*) desc, rand() " +
            "limit 1",
            nativeQuery = true)
    Optional<Book> findCurrentlyMostControversialBook();

    @Query(value = "select * from book order by rand() limit 3",
            nativeQuery = true)
    List<Book> findTop3Favourites();

    Book findFirstByTitleIgnoreCase(String title);

    Book findFirstByTitleContainingIgnoreCase(String title);
}
