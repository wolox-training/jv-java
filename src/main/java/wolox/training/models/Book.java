package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
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
import org.apache.commons.lang3.math.NumberUtils;
import static wolox.training.utils.Constants.*;

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

    @Column(nullable = false,unique = true)
    private String isbn;

    @Column(nullable = false)
    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<User> users;

    public void setGenre(String genre) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(genre),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "genre"));
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(author),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "author"));
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(image),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "image"));
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(title),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "title"));
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(subtitle),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "subtitle"));
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(publisher),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "publisher"));
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(year),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "year"));
        Preconditions.checkArgument(NumberUtils.isParsable(year),String.format(MESSAGE_CHECK_NOT_NUMBER,"year"));
        this.year = year;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages,String.format(MESSAGE_CHECK_IS_NULL_EMPTY,"pages"));
        Preconditions.checkArgument(pages > 0,String.format(MESSAGE_CHECK_GREATER_THAN_ZERO,"pages"));
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(isbn),
                String.format(MESSAGE_CHECK_IS_NULL_EMPTY, "isbn"));
        Preconditions.checkArgument(NumberUtils.isParsable(isbn),String.format(MESSAGE_CHECK_NOT_NUMBER,"isbn"));
        this.isbn = isbn;
    }
}
