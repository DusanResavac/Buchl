package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.Discussion;
import at.technikumwien.buchl.entity.DiscussionDTO;
import at.technikumwien.buchl.entity.DiscussionDTOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DiscussionResource {

    @Autowired
    DiscussionRepository repository;
    @Autowired
    DiscussionDTOFactory discussionDTOFactory;


    @GetMapping("/discussions/recent")
    public List<DiscussionDTO> retrieveRecentDiscussionsOverview () {
        List<Discussion> discussions = repository.findTop3ByOrderByDateDesc();
        List<DiscussionDTO> dDTOs = new ArrayList<>();
        for (Discussion d: discussions) {
            dDTOs.add(discussionDTOFactory.createDiscussionOverview(d));
        }
        return dDTOs;
    }

    public DiscussionDTO retrieveDiscussion(Long id) {
        Optional<Discussion> d = repository.findById(id);
        if (d.isEmpty()) {
            return null;
        }

        return discussionDTOFactory.createDiscussionDetail(d.get());
    }
}
