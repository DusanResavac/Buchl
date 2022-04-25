package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Discussion;
import at.technikumwien.buchl.entity.DiscussionDTO;
import at.technikumwien.buchl.entity.DiscussionDTOCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
public class DiscussionResource {

    @Autowired
    DiscussionRepository repository;
    @Autowired
    DiscussionDTOCreator discussionDTOCreator;


    @GetMapping("/api/discussions/recent")
    public List<DiscussionDTO> retrieveRecentDiscussionsOverview () {
        List<Discussion> discussions = repository.findTop3ByOrderByDateDesc();
        List<DiscussionDTO> dDTOs = new ArrayList<>();
        for (Discussion d: discussions) {
            dDTOs.add(discussionDTOCreator.createDiscussionOverview(d));
        }
        return dDTOs;
    }

    @GetMapping("/api/discussion/{id}")
    public DiscussionDTO retrieveDiscussion(@PathVariable("id") Long id) {
        Optional<Discussion> d = repository.findById(id);
        if (d.isEmpty()) {
            return null;
        }

        return discussionDTOCreator.createDiscussionDetail(d.get());
    }
}
