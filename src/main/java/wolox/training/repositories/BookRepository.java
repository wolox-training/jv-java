package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b "
            + " FROM Book b "
            + " WHERE (:publisher is null or UPPER(b.publisher) = UPPER(:publisher))"
            + " AND (:genre is null or UPPER(b.genre) = UPPER(:genre))"
            + " AND (:year is null or UPPER(b.year) = UPPER(:year))")
    List<Book> findByPublisherAndGenreAndYearAllIgnoreCase(@Param("publisher") String publisher,
            @Param("genre") String genre, @Param("year") String year);

}
