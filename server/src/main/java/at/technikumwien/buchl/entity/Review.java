package at.technikumwien.buchl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    public Review(String title, String text, LocalDate date, Integer likes, Integer rating, Book book, User user) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.likes = likes;
        this.rating = rating;
        this.book = book;
        this.user = user;
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

    @Column
    private Integer likes;

    @Column
    private Integer rating;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

}
