package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

/**
 * @author josefranciscodelvecchio
 * Controller for CRUD Book
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * This method find all Books
     * @return {@link List}<{@link Book}>
     */
    @GetMapping
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * This method find books that match with title param
     * @param title: Title of the Book
     * @return {@link List}<{@link Book}>
     */
    @GetMapping("/title/{title}")
    public List<Book> findByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    /**
     * This method find book by id param
     * @param id: Id of the book
     * @return {@link Book}
     * @throws BookNotFoundException when book not found
     */
    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    /**
     * This method creates an {@link Book} with the data from book param
     * @param book {@link Book}: Data to create
     * @return created {@link Book}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method delete book by id param
     * @param id: Id of the book
     * @throws BookNotFoundException when book to delete not found
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    /**
     * This method update data an book
     * @param book
     * @param id: Id of the book
     * @return {@link Book}: Data to update
     * @throws BookIdMismatchException when id param and book's id mismatch
     * @throws BookNotFoundException when book not found
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

}
