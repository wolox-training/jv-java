package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT b "
            + " FROM Book b "
            + " WHERE (:genre is null OR :genre = '' OR b.genre = :genre)"
            + " AND (:author is null OR :author = '' OR b.author = :author)"
            + " AND (:image is null OR :image = '' OR b.image = :image)"
            + " AND (:title is null OR :title = '' OR b.title = :title)"
            + " AND (:subtitle is null OR :subtitle = '' OR b.subtitle = :subtitle)"
            + " AND (:publisher is null OR :publisher = '' OR b.publisher = :publisher)"
            + " AND (:year is null OR :year = '' OR b.year = :year)"
            + " AND (:pages is null OR CAST(b.pages as int) = :pages)"
            + " AND (:isbn is null OR :isbn = '' OR b.isbn = :isbn)")
    Page<Book> findByAllFields(@Param("genre") String genre,
            @Param("author") String author, @Param("image") String image,
            @Param("title") String title, @Param("subtitle") String subtitle,
            @Param("publisher") String publisher, @Param("year") String year,
            @Param("pages") Integer pages, @Param("isbn") String isbn, Pageable pageable);

}
