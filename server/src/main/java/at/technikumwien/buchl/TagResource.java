package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
public class TagResource {
    @Autowired
    private TagRepository tagRepository;

    @CrossOrigin(origins = {"*"})
    @GetMapping("/api/tags")
    public List<RootTagDTO> retrieveRootTags () {
        List<Tag> rootTags = tagRepository.findAllByParentIsNull();
        List<RootTagDTO> rootTagDTOs = new ArrayList<>();

        rootTags.forEach(rTag -> {
            rootTagDTOs.add(TagDTOCreator.createRootTagDTO(rTag));
        });

        for (RootTagDTO rootTagDTO: rootTagDTOs) {
            List<Tag> childTags = tagRepository.findAllByParentId(rootTagDTO.getId());
            List<TagDTO> childTagDTOs = childTags.stream()
                    .map(TagDTOCreator::createTagDTO)
                    .collect(Collectors.toList());
            rootTagDTO.setTags(childTagDTOs);
        }

        return  rootTagDTOs;
    }
}
