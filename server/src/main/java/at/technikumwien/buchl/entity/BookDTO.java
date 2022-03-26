package at.technikumwien.buchl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private Language language;
    private Integer pages;
    private LocalDate releaseDate;
    private Author author;
    private String imageLink;
    private String imageAlt;
    private String isbn;
    private Long numberOfReviews;
    private Long averageRating;
}
