package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    private Book book;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        book = new Book();
        book.setAuthor("dante2");
        book.setImage("image2.jpg");
        book.setIsbn("9288882");
        book.setPublisher("Bloomsbury2");
        book.setTitle("comedia2");
        book.setSubtitle("divina comedia2");
        book.setPages(502);
        book.setGenre("poesia2");
        book.setYear("1302");
    }

    @Test
    public void whenFindOneWhichExists_thenBookIsReturned() throws Exception {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        final String url = "/api/books/1";
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("comedia2")));
    }

    @Test
    public void whenFindAllBooks_thenReturnList() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        final String url = "/api/books";
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void whenDeleteBookWhichNonExists_thenExceptionIsThrown() throws Exception {
        final String url = "/api/books/10";
        mvc.perform(delete(url))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
    }

    @Test
    public void whenDeleteBook_thenBookIsDeleted() throws Exception {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        final String url = "/api/books/1";

        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }

}
