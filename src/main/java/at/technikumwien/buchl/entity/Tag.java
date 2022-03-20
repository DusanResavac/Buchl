package at.technikumwien.buchl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tag")
public class Tag {

    public Tag(Tag parent, String name, List<Book> books) {
        this.parent = parent;
        this.name = name;
        this.books = books;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent_id")
    private Tag parent;

    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name="tags_books",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
}
