package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
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

    @GetMapping("/api/books/top3")
    public List<BookDTO> retrieveTop3Books() {
        List<Book> books = bookRepository.findTop3Favourites();
        return books.stream()
                .map(b -> bookDTOFactory.createBasicBook(b))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/books/search")
    public List<BookDTO> retrieveBooks(
            @RequestParam(value = "tag", required = false) Long tag,
            @RequestParam(value = "rating", required = false) Long rating,
            @RequestParam(value = "author", required = false) Long author,
            @RequestParam(value = "releaseYearFrom", required = false) Long releaseYearFrom,
            @RequestParam(value = "releaseYearUntil", required = false) Long releaseYearUntil,
            @RequestParam(value = "q", required = false) String search) {

        //var b = bookRepository.findAllByFilters(tag.get(), rating.get());
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream()
                .filter(b -> tag == null || listOfTagsContainsTagId(b.getTags(), tag))
                .filter(b -> rating == null ||
                        (b.getStars() != null &&
                                b.getNumberOfReviews() != null &&
                                b.getNumberOfReviews() != 0 &&
                                rating <= Math.round((float) b.getStars() / b.getNumberOfReviews())))
                .filter(b -> author == null || b.getAuthor().getId().equals(author))
                .filter(b -> releaseYearFrom == null || b.getReleaseDate().isAfter(LocalDate.of((int) (releaseYearFrom - 1), 12, 31)))
                .filter(b -> releaseYearUntil == null || b.getReleaseDate().isBefore(LocalDate.of((int) (releaseYearUntil + 1), 1, 1)))
                .filter(b -> search == null ||
                        b.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                        b.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                        b.getIsbn().toLowerCase().contains(search.toLowerCase()))
                .map(b -> bookDTOFactory.createForBookOverview(b))
                .collect(Collectors.toList());

        return bookDTOs;
    }

    @GetMapping("/api/book/{id}")
    public BookDTO retrieveBook (@PathVariable Long id) {
        Optional<Book> b = bookRepository.findById(id);
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookDetails(b.get());
    }

    @GetMapping("/recommendation/{id}")
    public List<BookDTO> getRecommendationForBook (@PathVariable Long id) {

        List<Book> books = bookRepository.findRecommendations((int) (Math.random() * 4), id);
        List<BookDTO> bookDTOs = new ArrayList<>();

        books.forEach(b -> bookDTOs.add(bookDTOFactory.createForBookOverview(b)));

        return bookDTOs;
    }

    @PostMapping("/api/recommendations")
    public List<RecommendationDTO> getRecommendationsForBooks (@RequestBody List<Long> ids) {

        List<RecommendationDTO> listOfRecommendations = new ArrayList<>();

        for (Long id: ids) {
            Optional<Book> book = bookRepository.findById(id);
            List<Book> recommendedBooks = bookRepository.findRecommendations((int) (Math.random() * 3) + 1, id);
            List<BookDTO> recommendedBookDTOs = new ArrayList<>();
            recommendedBooks.forEach(b -> recommendedBookDTOs.add(bookDTOFactory.createForBookOverview(b)));

            listOfRecommendations.add(new RecommendationDTO(id, book.map(Book::getTitle).orElse(null), recommendedBookDTOs));
        }

        return listOfRecommendations;
    }

    @PostMapping("/api/books-short")
    public List<BookDTO> getShortBookInfosByIds (@RequestBody List<Long> ids) {
        List<Book> books = bookRepository.findAllById(ids);

        return books.stream()
                .map(b -> bookDTOFactory.createForBookOverview(b))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/reviews/popular")
    public BookDTO retrieveCurrentlyPopularBook () {
        Optional<Book> b = bookRepository.findCurrentlyMostPopularBook();
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookReviews(b.get(), Optional.of(2));
    }

    @GetMapping("/api/reviews/controversial")
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

        List<Review> twoReviews = new ArrayList<>();

        // Get most positive and most negative review
        if (reviews.size() > 0) {
            twoReviews.add(reviews.get(0));
        }
        if (reviews.size() > 1) {
            twoReviews.add(reviews.get(reviews.size() - 1));
        }

        bookDTO.setReviews(twoReviews);

        return bookDTO;
    }

    @GetMapping("/api/book/{id}/reviews")
    public BookDTO retrieveBookWithReviews (@PathVariable Long id) {
        Optional<Book> b = bookRepository.findById(id);
        if (b.isEmpty()) {
            return null;
        }

        return bookDTOFactory.createForBookReviews(b.get(), Optional.empty());
    }

    @PostMapping("/api/books/with-discussions")
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
