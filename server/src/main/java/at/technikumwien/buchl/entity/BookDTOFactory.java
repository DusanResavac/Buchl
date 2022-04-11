package at.technikumwien.buchl.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookDTOFactory {

    public BookDTO createBasicBook(Book book) {
        Author a = book.getAuthor();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setReleaseDate(book.getReleaseDate());
        bookDTO.setImageLink(book.getImageLink());
        bookDTO.setImageAlt(book.getImageAlt());
        bookDTO.setAuthor(new Author(a.getId(), a.getFirstName(), a.getLastName(), null, null, null));
        return bookDTO;
    }

    public BookDTO createForBookOverview(Book book) {
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
            bookDTO.setAverageRating((double) (Math.round(((double) book.getStars() / (double) book.getNumberOfReviews()) * 10)) / 10);
        }
        return bookDTO;
    }

    public BookDTO createForBookDetails(Book book) {
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
        List<Tag> tags = book.getTags()
                .stream()
                .map(t -> new Tag(t.getId(),
                        t.getParent() != null ?
                                new Tag(t.getParent().getId(), null, t.getParent().getName(), null) :
                                null,
                        t.getName(),
                        null))
                .collect(Collectors.toList());
        bookDTO.setTags(tags);
        // get the necessary information from the discussions
        List<Discussion> discussions = book.getDiscussions()
                .stream()
                .map(d -> new Discussion(
                        d.getId(),
                        d.getTitle(),
                        d.getText(),
                        d.getDate(),
                        // the user only needs to have the id, nickname and image assigned
                        new User(d.getUser().getId(), d.getUser().getNickname(), d.getUser().getImage(), null, null, null),
                        null,
                        // the comments are only accessed to retrieve the number of comments -> replace each comment with an empty one
                        d.getComments()
                                .stream()
                                .map(c -> new Comment(null, null, null, null, null))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        bookDTO.setDiscussions(discussions);
        if (book.getStars() != null && book.getNumberOfReviews() != null && book.getNumberOfReviews() != 0) {
            bookDTO.setAverageRating((double) (Math.round(((double) book.getStars() / (double) book.getNumberOfReviews()) * 10)) / 10);
        }
        bookDTO.setReviews(book.getReviews()
                .stream()
                .filter(r -> r.getDate() != null &&
                        r.getUser() != null &&
                        r.getTitle() != null)
                .map(BookDTOFactory::createBareReview)
                .collect(Collectors.toList()));
        return bookDTO;
    }

    public BookDTO createForBookDiscussions(Book book) {
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
        for (Discussion dBook : book.getDiscussions()) {
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

    public BookDTO createForBookReviews(Book book, Optional<Integer> reviews) {
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
                var review = book.getReviews().get(i);
                reviewList.add(createBareReview(review));
            }

            bookDTO.setReviews(reviewList);
        } else {
            bookDTO.setReviews(book.getReviews()
                    .stream()
                    .filter(r -> r.getDate() != null &&
                            r.getUser() != null &&
                            r.getTitle() != null)
                    .map(BookDTOFactory::createBareReview)
                    .collect(Collectors.toList()));
        }
        bookDTO.setDiscussions(null);
        return bookDTO;
    }

    private static Review createBareReview(Review review) {
        User user = review.getUser();
        if (user != null) {
            user = new User(
                    user.getId(),
                    user.getNickname(),
                    user.getImage(),
                    null,
                    null,
                    null);
        }

        return new Review(
                review.getId(),
                review.getTitle(),
                review.getText(),
                review.getDate(),
                review.getLikes(),
                review.getRating(),
                null,
                user);
    }
}
