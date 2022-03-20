package at.technikumwien.buchl.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    public Book(String title, Language language, Integer pages, LocalDate releaseDate, String description, Author author, String imageLink, String imageAlt) {
        this.title = title;
        this.language = language;
        this.pages = pages;
        this.releaseDate = releaseDate;
        this.description = description;
        this.author = author;
        this.imageLink = imageLink;
        this.imageAlt = imageAlt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column
    private Integer pages;

    @Column
    private LocalDate releaseDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(columnDefinition = "TEXT")
    private String imageLink;

    @Column
    private String imageAlt;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name="tags_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
    private List<Review> review;

}
