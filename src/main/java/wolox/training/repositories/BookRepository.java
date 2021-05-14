package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

}
