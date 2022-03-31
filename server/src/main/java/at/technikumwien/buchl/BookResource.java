package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BookResource {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BookDTOFactory bookDTOFactory;

    @GetMapping("/books/search")
    public List<BookDTO> retrieveBooks(
            @RequestParam("tag") Optional<Long> tag,
            @RequestParam("rating") Optional<Long> rating,
            @RequestParam("author") Optional<Long> author,
            @RequestParam("releaseYearFrom") Optional<Long> releaseYearFrom,
            @RequestParam("releaseYearUntil") Optional<Long> releaseYearUntil,
            @RequestParam("q") Optional<String> search) {

        //var b = bookRepository.findAllByFilters(tag.get(), rating.get());
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream()
                .filter(b -> tag.isEmpty() || listOfTagsContainsTagId(b.getTags(), tag.get()))
                .filter(b -> rating.isEmpty() ||
                        (b.getStars() != null &&
                                b.getNumberOfReviews() != null &&
                                b.getNumberOfReviews() != 0 &&
                                rating.get() <= b.getStars() / b.getNumberOfReviews()))
                .filter(b -> author.isEmpty() || b.getAuthor().getId().equals(author.get()))
                .filter(b -> releaseYearFrom.isEmpty() || b.getReleaseDate().isAfter(LocalDate.of((int) (releaseYearFrom.get() - 1), 12, 31)))
                .filter(b -> releaseYearUntil.isEmpty() || b.getReleaseDate().isBefore(LocalDate.of((int) (releaseYearUntil.get() + 1), 1, 1)))
                .filter(b -> search.isEmpty() ||
                        b.getTitle().contains(search.get()) ||
                        b.getDescription().contains(search.get()) ||
                        b.getIsbn().contains(search.get()))
                .map(b -> bookDTOFactory.createForBookOverview(b))
                .collect(Collectors.toList());

        return bookDTOs;
    }

    @GetMapping("/book/retrieve/{id}")
    public BookDTO retrieveBook (@PathVariable Long id) {
        Optional<Book> b = bookRepository.findById(id);
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookDetails(b.get());
    }

    @GetMapping("/reviews/popular")
    public BookDTO retrieveCurrentlyPopularBook () {
        Optional<Book> b = bookRepository.findCurrentlyMostPopularBook();
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookReviews(b.get(), Optional.of(2));
    }

    @GetMapping("/reviews/controversial")
    public BookDTO retrieveCurrentlyControversialBook() {
        Optional<Book> b = bookRepository.findCurrentlyMostControversialBook();
        if (b.isEmpty()) {
            return null;
        }
        BookDTO bookDTO = bookDTOFactory.createForBookReviews(b.get(), Optional.empty());
        List<Review> reviews = bookDTO.getReviews()
                .stream()
                .sorted((item1, item2) -> item2.getRating() - item1.getRating())
                .filter(r -> r.getDate() != null && r.getUser() != null && r.getTitle() != null)
                .collect(Collectors.toList());

        List<Review> twoReviews = new ArrayList<>(){{
            add(reviews.get(0));
            add(reviews.get(reviews.size() - 1));
        }};
        bookDTO.setReviews(twoReviews);

        return bookDTO;
    }

    @GetMapping("/book/retrieve/{id}/reviews")
    public BookDTO retrieveBookWithReviews (@PathVariable Long id) {
        Optional<Book> b = bookRepository.findById(id);
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookReviews(b.get(), Optional.empty());
    }

    @PostMapping("/books/with-discussions")
    public List<BookDTO> retrieveBookWithDiscussion (@RequestBody List<Long> ids) {
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Long id: ids) {
            Optional<Book> book = bookRepository.findById(id);
            book.ifPresent(value -> bookDTOs.add(bookDTOFactory.createForBookDiscussions(value)));
        }

        return bookDTOs;
    }

    private boolean listOfTagsContainsTagId(List<Tag> tags, Long tagId) {
        for (Tag t : tags) {
            if (t.getId().equals(tagId)) {
                return true;
            }
        }
        return false;
    }

}
