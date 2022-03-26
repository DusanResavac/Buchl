package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
