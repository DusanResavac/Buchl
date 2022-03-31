package at.technikumwien.buchl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionDTO {
    private Long id;
    private String title;
    private String text;
    private LocalDate date;
    private User user;
    private Book book;
    private String timePassed;
    private List<CommentDTO> comments;
}
