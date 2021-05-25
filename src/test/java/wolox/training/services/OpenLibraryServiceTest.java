package wolox.training.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wolox.training.models.Book;


@SpringBootTest
public class OpenLibraryServiceTest {

    @Autowired
    private OpenLibraryService openLibraryService;

    @Test
    public void givenIsbn_whenFindInfoBookInExternalService_thenBookIsReturned() throws Exception {
        final Book book = openLibraryService.bookInfo("0385472579");

        assertTrue(book.getTitle().contains("wiremock"));
    }

}
