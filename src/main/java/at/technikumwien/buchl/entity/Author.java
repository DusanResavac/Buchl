package at.technikumwien.buchl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author {

    public Author(String firstName, String lastName, String imageLink, String imageAlt, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageLink = imageLink;
        this.imageAlt = imageAlt;
        this.books = books;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String imageLink;

    @Column
    private String imageAlt;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Book> books;
}
