package at.technikumwien.buchl.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "discussion")
public class Discussion {

    public Discussion(String title, String text, LocalDate date, User user, Book book, List<Comment> comments) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.user = user;
        this.book = book;
        this.comments = comments;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.MERGE)
    private List<Comment> comments;

}
