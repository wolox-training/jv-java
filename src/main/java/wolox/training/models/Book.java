package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.apache.commons.lang3.math.NumberUtils;
import wolox.training.utils.Constants;

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
    @JsonIgnore
    private List<User> users;

    public void setGenre(String genre) {
        Preconditions.checkNotNull(genre,String.format(Constants.MESSAGE_CHECK_IS_NULL,"genre"));
        Preconditions.checkArgument(!genre.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"genre"));
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author,String.format(Constants.MESSAGE_CHECK_IS_NULL,"author"));
        Preconditions.checkArgument(!author.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"author"));
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image,String.format(Constants.MESSAGE_CHECK_IS_NULL,"image"));
        Preconditions.checkArgument(!image.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"image"));
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title,String.format(Constants.MESSAGE_CHECK_IS_NULL,"title"));
        Preconditions.checkArgument(!title.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"title"));
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle,String.format(Constants.MESSAGE_CHECK_IS_NULL,"subtitle"));
        Preconditions.checkArgument(!subtitle.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"subtitle"));
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher,String.format(Constants.MESSAGE_CHECK_IS_NULL,"publisher"));
        Preconditions.checkArgument(!publisher.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"publisher"));
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year,String.format(Constants.MESSAGE_CHECK_IS_NULL,"year"));
        Preconditions.checkArgument(!year.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"year"));
        Preconditions.checkArgument(NumberUtils.isParsable(year),String.format(Constants.MESSAGE_CHECK_NOT_NUMBER,"year"));
        this.year = year;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages,String.format(Constants.MESSAGE_CHECK_IS_NULL,"pages"));
        Preconditions.checkArgument(pages > 0,String.format(Constants.MESSAGE_CHECK_GREATER_THAN_ZERO,"pages"));
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn,String.format(Constants.MESSAGE_CHECK_IS_NULL,"isbn"));
        Preconditions.checkArgument(!isbn.isEmpty(),String.format(Constants.MESSAGE_CHECK_IS_EMPTY,"isbn"));
        Preconditions.checkArgument(NumberUtils.isParsable(isbn),String.format(Constants.MESSAGE_CHECK_NOT_NUMBER,"isbn"));
        this.isbn = isbn;
    }
}
