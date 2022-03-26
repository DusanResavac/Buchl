package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Book;
import at.technikumwien.buchl.entity.BookDTO;
import at.technikumwien.buchl.entity.BookDTOFactory;
import at.technikumwien.buchl.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BuchResource {

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
            @RequestParam("search") Optional<String> search) {

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

    private boolean listOfTagsContainsTagId(List<Tag> tags, Long tagId) {
        for (Tag t : tags) {
            if (t.getId().equals(tagId)) {
                return true;
            }
        }
        return false;
    }

}
