package at.technikumwien.buchl.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {

    public User(String nickname, String image, List<Discussion> discussions, List<Comment> comments) {
        this.nickname = nickname;
        this.image = image;
        this.discussions = discussions;
        this.comments = comments;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String image;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Discussion> discussions;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Review> reviews;


}
