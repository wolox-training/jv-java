package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import wolox.training.exceptions.BookAlreadyOwnedException;

/**
 * Model class from table User
 * @author josefranciscodelvecchio
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    @ManyToMany
    private List<Book> books;

    public List<Book> getBooks(){
        return Collections.unmodifiableList(books);
    }

    /**
     * This method add book in collection {@link #books}
     * @param book {@link Book}: Data to create
     * @throws BookAlreadyOwnedException when book is already associated to user
     */
    public void addBook(Book book){
        if(books.contains(book)){
            throw new BookAlreadyOwnedException();
        }else{
            books.add(book);
        }
    }

    /**
     * This method remove book of collection {@link #books}
     * @param book {@link Book}: Book to remove
     */
    public void removeBook(Book book){
        books.remove(book);
    }

}
