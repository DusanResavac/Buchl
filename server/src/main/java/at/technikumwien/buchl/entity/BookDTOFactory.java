package at.technikumwien.buchl.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookDTOFactory {
    public BookDTO createForBookOverview (Book book) {
        BookDTO bookDTO = new BookDTO();
        Author author = new Author();
        author.setFirstName(book.getAuthor().getFirstName());
        author.setLastName(book.getAuthor().getLastName());
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(author);
        bookDTO.setReleaseDate(book.getReleaseDate());
        bookDTO.setImageLink(book.getImageLink());
        bookDTO.setImageAlt(book.getImageAlt());
        if (book.getStars() != null && book.getNumberOfReviews() != null && book.getNumberOfReviews() != 0) {
            bookDTO.setAverageRating((double) (Math.round(((double) book.getStars() / (double) book.getNumberOfReviews())*10)) / 10);
        }
        return bookDTO;
    }

    public BookDTO createForBookDetails (Book book) {
        BookDTO bookDTO = new BookDTO();
        Author author = new Author();
        author.setId(book.getAuthor().getId());
        author.setFirstName(book.getAuthor().getFirstName());
        author.setLastName(book.getAuthor().getLastName());
        author.setImageLink(book.getAuthor().getImageLink());
        author.setImageAlt(book.getAuthor().getImageAlt());
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        String description = book.getDescription().replaceAll("((\n\r)|(\n))", "<br>");
        bookDTO.setDescription(description);
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setPages(book.getPages());
        bookDTO.setReleaseDate(book.getReleaseDate());
        bookDTO.setAuthor(author);
        bookDTO.setImageLink(book.getImageLink());
        bookDTO.setImageAlt(book.getImageAlt());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTags(book.getTags());
        bookDTO.setDiscussions(book.getDiscussions());
        if (book.getStars() != null && book.getNumberOfReviews() != null && book.getNumberOfReviews() != 0) {
            bookDTO.setAverageRating((double) (Math.round(((double) book.getStars() / (double) book.getNumberOfReviews())*10)) / 10);
        }
        return bookDTO;
    }

    public BookDTO createForBookDiscussions (Book book) {
        BookDTO bookDTO = new BookDTO();
        Author a = new Author();
        Author aBook = book.getAuthor();
        a.setId(aBook.getId());
        a.setFirstName(aBook.getFirstName());
        a.setLastName(aBook.getLastName());
        a.setImageLink(aBook.getImageLink());
        a.setImageAlt(aBook.getImageAlt());
        bookDTO.setAuthor(a);
        List<Discussion> discussions = new ArrayList<>();
        for (Discussion dBook: book.getDiscussions()) {
            Discussion d = new Discussion();
            User u = new User();
            u.setNickname(dBook.getUser().getNickname());
            u.setImage(dBook.getUser().getImage());
            d.setUser(u);
            d.setId(dBook.getId());
            d.setTitle(dBook.getTitle());
            // A list with empty comments is enough for the overview, since only the number of comments
            // (length of the list) needs to be displayed
            d.setComments(dBook.getComments().stream()
                    .map(c -> new Comment())
                    .collect(Collectors.toList()));
            discussions.add(d);
        }
        bookDTO.setDiscussions(discussions);
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setImageLink(book.getImageLink());
        bookDTO.setImageAlt(book.getImageAlt());


        return bookDTO;
    }

    public BookDTO createForBookReviews (Book book, Optional<Integer> reviews) {
        BookDTO bookDTO = createForBookDetails(book);
        // get first x reviews if the parameter is present
        if (reviews.isPresent()) {
            List<Review> bookReviews = book.getReviews()
                    .stream()
                    .filter(r -> r.getDate() != null &&
                            r.getUser() != null &&
                            r.getTitle() != null)
                    .collect(Collectors.toList());
            List<Review> reviewList = new ArrayList<>();

            for (int i = 0; i < reviews.get() && i < bookReviews.size(); i++) {
                reviewList.add(book.getReviews().get(i));
            }

            bookDTO.setReviews(reviewList);
        } else {
            bookDTO.setReviews(book.getReviews());
        }
        bookDTO.setDiscussions(null);
        return bookDTO;
    }
}
