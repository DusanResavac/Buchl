package at.technikumwien.buchl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    public Book(String title, Language language, Integer pages, LocalDate releaseDate, String description, Author author, String imageLink, String imageAlt, String isbn) {
        this.title = title;
        this.language = language;
        this.pages = pages;
        this.releaseDate = releaseDate;
        this.description = description;
        this.author = author;
        this.imageLink = imageLink;
        this.imageAlt = imageAlt;
        this.isbn = isbn;
        this.numberOfReviews = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column
    private Integer pages;

    @Column
    private LocalDate releaseDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(columnDefinition = "TEXT")
    private String imageLink;

    @Column
    private String imageAlt;

    @Column
    private String isbn;

    @Column
    private Long stars;

    @Column
    private Long numberOfReviews;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name="tags_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @JsonManagedReference
    @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
    @OrderBy("date DESC")
    private List<Review> reviews;

    @JsonManagedReference
    @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
    @OrderBy("date DESC")
    private List<Discussion> discussions;

}
