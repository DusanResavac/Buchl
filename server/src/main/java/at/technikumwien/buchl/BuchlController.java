package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Author;
import at.technikumwien.buchl.entity.Book;
import at.technikumwien.buchl.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping({"/"})
    public String getHomepage (Model model) {
        List<Book> books = bookRepository.findTop3Favourites();
        model.addAttribute("title", "Startseite");
        model.addAttribute("books", books);

        return "homepage";
    }

    @GetMapping({"/books"})
    public String getBooksSearchSite (Model model) {
        List<Tag> tags = tagRepository.findAllByParentIsNotNull();
        List<Tag> parentTags = tagRepository.findAllByParentIsNull();
        List<Author> authors = authorRepository.findAll();
        int[] stars = new int[] {1, 2, 3, 4, 5};
        model.addAttribute("title", "Bücher stöbern und filtern");
        model.addAttribute("tags", tags);
        model.addAttribute("parentTags", parentTags);
        model.addAttribute("stars", stars);
        model.addAttribute("authors", authors);

        return "books";
    }

    @GetMapping({"/book/{id}"})
    public String getBooksSearchSite (@PathVariable("id") Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NoSuchElementException("Book with id '" + id + "' not found.");
        }
        model.addAttribute("title", "Bücher stöbern und filtern");
        book.get().setDescription(book.get().getDescription().replaceAll("((\n\r)|\r)", "<br>"));
        model.addAttribute("book", book.get());

        return "book";
    }

    @GetMapping({"/favorites"})
    public String getFavoriteSite (Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("something", "Favoriten");
        model.addAttribute("books", books);

        return "book";
    }
}
