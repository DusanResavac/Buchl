package at.technikumwien.buchl.entity;

import org.springframework.stereotype.Component;

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
            bookDTO.setAverageRating(Math.round((double) book.getStars() / (double) book.getNumberOfReviews()));
        }
        return bookDTO;
    }
}
