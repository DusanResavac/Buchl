package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findFirstByNameIgnoreCase(String name);
}
