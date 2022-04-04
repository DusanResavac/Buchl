package at.technikumwien.buchl.entity;

public class TagDTOFactory {

    public static RootTagDTO createRootTagDTO (Tag t) {
        return new RootTagDTO(t.getId(), t.getName(), null);
    }

    public static TagDTO createTagDTO (Tag t) {
        return new TagDTO(t.getId(), t.getName());
    }
}
