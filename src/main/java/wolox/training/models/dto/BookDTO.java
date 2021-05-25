package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import org.springframework.util.CollectionUtils;

@Data
public class BookDTO {

    private String isbn;
    private String title;
    private String subtitle;
    private List<DataName>  publishers;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("number_of_pages")
    private Integer numberOfPages;
    private List<DataName> authors;
    private List<DataName> subjects;
    private Cover cover;

    public String getPublishers(){
        return !CollectionUtils.isEmpty(this.publishers) ? this.publishers.get(0).getName() : null;
    }

    public String getAuthors(){
        return !CollectionUtils.isEmpty(this.authors) ? this.authors.get(0).getName() : null;
    }

    public String getSubjects(){
        return !CollectionUtils.isEmpty(this.subjects) ? this.subjects.get(0).getName() : null;
    }

    public String getCover(){
        return Objects.nonNull(this.cover) ? this.cover.getSmall() : null;
    }

}

@Data
class DataName {
    private String name;
}

@Data
class Cover {
    private String small;
    private String medium;
    private String large;
}
