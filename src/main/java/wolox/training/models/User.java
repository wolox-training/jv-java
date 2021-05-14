package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Users from database")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The user username")
    private String userName;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The user name: name of user")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The user birthdate: date birth of user")
    private LocalDate birthdate;

    @Column(nullable = false)
    @ManyToMany
    @ApiModelProperty(notes = "The user books: books associated to user")
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

    public void setUserName(String userName) {
        Preconditions.checkNotNull(userName,"Please check the userName supplied, its null!");
        this.userName = userName;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name,"Please check the name supplied, its null!");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate,"Please check the birthdate supplied, its null!");
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkNotNull(books,"Please check the list book supplied, its null!");
        this.books = books;
    }
}
