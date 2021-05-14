package wolox.training.models;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Model class from table Book
 * @author josefranciscodelvecchio
 */
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    @ManyToMany(mappedBy = "books")
    private List<User> users;

    public void setGenre(String genre) {
        Preconditions.checkNotNull(genre,"Please check the genre supplied, its null!");
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author,"Please check the author supplied, its null!");
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image,"Please check the image supplied, its null!");
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title,"Please check the title supplied, its null!");
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle,"Please check the subtitle supplied, its null!");
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher,"Please check the publisher supplied, its null!");
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year,"Please check the year supplied, its null!");
        this.year = year;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages,"Please check the pages supplied, its null!");
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn,"Please check the isbn supplied, its null!");
        this.isbn = isbn;
    }
}
