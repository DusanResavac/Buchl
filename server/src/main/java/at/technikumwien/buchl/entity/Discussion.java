package at.technikumwien.buchl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonManagedReference
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.MERGE)
    private List<Comment> comments;

}