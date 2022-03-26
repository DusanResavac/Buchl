package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByParentIsNotNull ();
    List<Tag> findAllByParentIsNull ();
    Tag findFirstByNameIgnoreCase(String name);
}
