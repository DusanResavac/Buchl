package at.technikumwien.buchl.entity;

import at.technikumwien.buchl.TimeAgo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiscussionDTOCreator {

    public DiscussionDTO createDiscussionOverview(Discussion d) {
        DiscussionDTO dDto = new DiscussionDTO();
        dDto.setId(d.getId());
        dDto.setTitle(d.getTitle());
        dDto.setDate(d.getDate());
        User u = new User();
        u.setImage(d.getUser().getImage());
        u.setNickname(d.getUser().getNickname());
        dDto.setUser(u);
        List<CommentDTO> comments = new ArrayList<>();
        // A list with empty comments is enough for the overview, since only the number of comments
        // (length of the list) needs to be displayed
        for (Comment c: d.getComments()) {
            comments.add(new CommentDTO());
        }
        dDto.setComments(comments);
        if (d.getBook() != null) {
            Book b = new Book();
            b.setId(d.getBook().getId());
            b.setTitle(d.getBook().getTitle());
            b.setImageLink(d.getBook().getImageLink());
            b.setImageAlt(d.getBook().getImageAlt());
            dDto.setBook(b);
        }
        return dDto;
    }

    public DiscussionDTO createDiscussionDetail(Discussion d) {
        DiscussionDTO dDto = createDiscussionOverview(d);
        dDto.setText(d.getText().replaceAll("((\n\r)|(\n))", "<br>"));
        if (dDto.getDate() == null) {
            dDto.setTimePassed("Datum der Erstellung unbekannt");
        } else {
            LocalDate ld = dDto.getDate();
            LocalDate now = LocalDate.now();
            dDto.setTimePassed(TimeAgo.toDuration(
                    ChronoUnit.MILLIS.between(
                            LocalDateTime.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth(), 0, 0),
                            LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0)),
                    true));
        }
        List<CommentDTO> comments = new ArrayList<>();

        for (Comment cOriginal: d.getComments()) {
            CommentDTO c = new CommentDTO();
            User u = cOriginal.getUser();

            c.setId(cOriginal.getId());
            c.setDateTime(cOriginal.getDateTime());
            c.setText(cOriginal.getText());
            c.setUser(new User(u.getId(), u.getNickname(), u.getImage(), null, null, null));
            c.setTimePassed(TimeAgo.toDuration(ChronoUnit.MILLIS.between(c.getDateTime(), LocalDateTime.now()), false));
            comments.add(c);
        }

        dDto.setComments(comments);
        return dDto;
    }
}
