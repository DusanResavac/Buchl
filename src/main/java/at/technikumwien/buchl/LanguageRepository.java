package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findFirstByNameIgnoreCase(String name);
}
