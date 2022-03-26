package at.technikumwien.buchl.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @JsonManagedReference
    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Book> books;
}
