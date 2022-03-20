package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping({"/"})
    public String getBooks (Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("something", "this is coming from the controller");
        model.addAttribute("books", books);

        return "book";
    }
}
