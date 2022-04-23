package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class BuchlController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BookResource bookResource;
    @Autowired
    private DiscussionResource discussionResource;
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private TagResource tagResource;

    @GetMapping({"/"})
    public String getHomepage (Model model) {
        List<Book> books = bookRepository.findTop3Favourites();
        model.addAttribute("title", "Startseite");
        model.addAttribute("books", books);

        return "homepage";
    }

    @GetMapping({"/books"})
    public String getBookSearchSite(
            @RequestParam(value = "tag", required = false) Long tag,
            @RequestParam(value = "rating", required = false) Long rating,
            @RequestParam(value = "author", required = false) Long authorId,
            @RequestParam(value = "releaseYearFrom", required = false) Long releaseYearFrom,
            @RequestParam(value = "releaseYearUntil", required = false) Long releaseYearUntil,
            @RequestParam(value = "q", required = false) String search,
            Model model) {
        List<Tag> tags = tagRepository.findAllByParentIsNotNull();
        List<Tag> parentTags = tagRepository.findAllByParentIsNull();
        List<Author> authors = authorRepository.findAll();
        int[] stars = new int[] {1, 2, 3, 4, 5};
        List<BookDTO> books = null;

        books = bookResource.retrieveBooks(tag, rating, authorId, releaseYearFrom, releaseYearUntil, search);
        /*if (tag != null || rating != null || authorId != null || releaseYearFrom != null || releaseYearUntil != null || search != null) {
            books = bookResource.retrieveBooks(tag, rating, authorId, releaseYearFrom, releaseYearUntil, search);
        }*/

        model.addAttribute("title", "Bücher stöbern und filtern");

        model.addAttribute("tagId", tag);
        model.addAttribute("rating", rating);
        model.addAttribute("authorId", authorId);
        model.addAttribute("releaseYearFrom", releaseYearFrom);
        model.addAttribute("releaseYearUntil", releaseYearUntil);
        model.addAttribute("search", search);

        model.addAttribute("tags", tags);
        model.addAttribute("parentTags", parentTags);
        model.addAttribute("stars", stars);
        model.addAttribute("authors", authors);
        model.addAttribute("booksByQueryParams", books);
        model.addAttribute("hasSearchResults", books != null && books.size() > 0);

        return "books";
    }

    @GetMapping({"/book/{id}"})
    public String getBookDetailPage(@PathVariable("id") Long id, Model model) {
        BookDTO book = bookResource.retrieveBook(id);
        if (book == null) {
            throw new NoSuchElementException("Book with id '" + id + "' not found.");
        }
        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        return "book";
    }

    @GetMapping({"/book/{id}/reviews"})
    public String getBookReviewsPage (@PathVariable("id") Long id, Model model) {
        BookDTO book = bookResource.retrieveBookWithReviews(id);
        if (book == null) {
            throw new NoSuchElementException("Book with id '" + id + "' not found.");
        }
        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        return "book-reviews";
    }

    @GetMapping("/discussions")
    public String getDiscussions (Model model) {
        List<DiscussionDTO> newDiscussionDTOs = discussionResource.retrieveRecentDiscussionsOverview();

        model.addAttribute("title", "Diskussionen");
        model.addAttribute("newDiscussions", newDiscussionDTOs);

        return "discussions";
    }

    @GetMapping("/discussion/{id}")
    public String getDiscussion (@PathVariable("id") Long id, Model model) {
        DiscussionDTO discussionDTO = discussionResource.retrieveDiscussion(id);

        model.addAttribute("title", "Diskussion zu " + discussionDTO.getBook().getTitle() + ": " + discussionDTO.getTitle());
        model.addAttribute("discussion", discussionDTO);

        return "discussion";
    }

    @GetMapping("/reviews")
    public String getReviews (Model model) {
        BookDTO popularBook = bookResource.retrieveCurrentlyPopularBook();
        BookDTO controversialBook = bookResource.retrieveCurrentlyControversialBook();

        model.addAttribute("title", "Rezensionen");
        model.addAttribute("popularBook", popularBook);
        model.addAttribute("controversialBook", controversialBook);

        return "reviews";
    }

    @GetMapping({"/favourites"})
    public String getFavoriteSite (Model model) {
        model.addAttribute("title", "Favoriten");

        return "favourites";
    }

    @GetMapping({"/recommendations"})
    public String getRecommendations (Model model) {
        model.addAttribute("title", "Empfehlungen");

        return "recommendations";
    }

    @GetMapping({"/tags"})
    public String getTagSite (Model model) {
        List<RootTagDTO> rootTagDTOs = tagResource.retrieveRootTags();

        model.addAttribute("rootTagDTOs", rootTagDTOs);
        model.addAttribute("title", "Themen und Genres");

        return "tags";
    }
}
