package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Author;
import at.technikumwien.buchl.entity.RootTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
public class AuthorResource {
    @Autowired
    private AuthorRepository authorRepository;

    @CrossOrigin(origins = {"*"})
    @GetMapping("/api/authors")
    public List<Author> retrieveAuthorsOverview () {
        return authorRepository.findAll()
                .stream()
                .map(author -> new Author(author.getId(), author.getFirstName(), author.getLastName(), null, null, null))
                .collect(Collectors.toList());
    }
}
