package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.models.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Book bookDB;

    @BeforeEach
    public void setUp() {
        bookDB = new Book();
        bookDB.setAuthor("dante");
        bookDB.setImage("image.jpg");
        bookDB.setIsbn("928888");
        bookDB.setPublisher("Bloomsbury");
        bookDB.setTitle("comedia");
        bookDB.setSubtitle("divina comedia");
        bookDB.setPages(500);
        bookDB.setGenre("poesia");
        bookDB.setYear("1300");

        entityManager.persist(bookDB);
        entityManager.flush();
    }

    @Test
    public void whenFindByAuthor_thenReturnBook() {
        // when
        Book bookFound = bookRepository.findByAuthor("dante").orElse(null);

        //then
        assertEquals(bookFound.getAuthor(),bookDB.getAuthor());
    }

    @Test
    public void whenFindAll_thenReturnBooks() {
        // when
        List<Book> booksFound = bookRepository.findAll();

        //then
        assertTrue(booksFound.size() > 0);
    }

    @Test
    public void whenCreateBook_thenReturnBookPersisted(){
        Book book = new Book();
        book.setAuthor("dante2");
        book.setImage("image2.jpg");
        book.setIsbn("9288882");
        book.setPublisher("Bloomsbury2");
        book.setTitle("comedia2");
        book.setSubtitle("divina comedia2");
        book.setPages(502);
        book.setGenre("poesia2");
        book.setYear("1302");

        Book bookPersisted = entityManager.persist(book);
        entityManager.flush();
        assertNotNull(bookPersisted.getId());
    }

    @Test
    public void whenCreateBookWithoutData_thenThrowException() {
        //given
        Book book = new Book();

        //when
        assertThrows(PersistenceException.class,() -> {
            entityManager.persist(book);
            entityManager.flush();
        });

    }
}
