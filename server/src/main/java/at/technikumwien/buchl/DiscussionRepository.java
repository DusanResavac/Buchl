package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    public List<Discussion> findTop3ByOrderByDateDesc();
}
