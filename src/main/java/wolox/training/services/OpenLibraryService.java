package wolox.training.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.dto.BookDTO;

@Service
public class OpenLibraryService{

    @Value("${resource.api.url}")
    private String urlBase;

    private static final String isbnParam = "ISBN:";

    public Book bookInfo(String isbn) {
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(String.format(urlBase,isbn), String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String jsonObject;
            BookDTO bookDTO = null;
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
               JsonNode parent = objectMapper.readTree(responseEntity.getBody());
               jsonObject = parent.get(isbnParam.concat(isbn)).toString();

               bookDTO = objectMapper.readValue(jsonObject,BookDTO.class);
               bookDTO.setIsbn(isbn);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new BookNotFoundException();
            }

            return mapDTOtoModel(bookDTO);
        }else{
            throw new BookNotFoundException();
        }
    }

    private Book mapDTOtoModel(BookDTO dto){
        Book model = new Book();
        model.setAuthor(dto.getAuthors());
        model.setGenre(dto.getSubjects());
        model.setIsbn(dto.getIsbn());
        model.setPages(dto.getNumberOfPages());
        model.setPublisher(dto.getPublishers());
        model.setTitle(dto.getTitle());
        model.setSubtitle(dto.getSubtitle());
        model.setYear(dto.getPublishDate());
        model.setImage(dto.getCover());
        return model;
    }

}
