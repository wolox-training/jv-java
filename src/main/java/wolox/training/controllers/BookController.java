package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import wolox.training.services.OpenLibraryService;

/**
 * @author josefranciscodelvecchio
 * Controller for CRUD Book
 */
@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    /**
     * This method find all Books
     * @return {@link Book}
     */
    @GetMapping
    @ApiOperation(value = "Return all books", response = Book[].class)
    @ApiResponse(code = 200,message = "Successfully retrieved books")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * This method find books that match with title param
     * @param title: Title of the Book
     * @return {@link List}<{@link Book}>
     */
    @GetMapping("/title/{title}")
    @ApiOperation(value = "Giving an title, return matching books", response = Book[].class)
    @ApiResponse(code = 200,message = "Successfully retrieved books")
    public List<Book> findByTitle(@ApiParam(value = "title to find the books", required = true) @PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    /**
     * This method find book by id param
     * @param id: Id of the book
     * @return {@link Book}
     * @throws BookNotFoundException when book not found
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an id, return the book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully retrieved books"),
            @ApiResponse(code = 404,message = "Book not found")
    })
    public Book findOne(@ApiParam(value = "id to find the book", required = true, example = "1") @PathVariable Long id) {
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
    @ApiOperation(value = "Create an book with the data from book param, return book created", response = Book.class)
    @ApiResponse(code = 201,message = "Book created successfully")
    public Book create(@ApiParam(value = "book data to create", required = true) @RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method delete book by id param
     * @param id: Id of the book
     * @throws BookNotFoundException when book to delete not found
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the book that match id param")
    @ApiResponse(code = 404,message = "Book not found")
    public void delete(@ApiParam(value = "id of the book to delete", required = true, example = "1") @PathVariable Long id) {
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
    @ApiOperation(value = "Update book data, return book updated", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400,message = "Id mismatch with book's id"),
            @ApiResponse(code = 404,message = "Book not found")
    })
    public Book updateBook(@ApiParam(value = "book data to update", required = true) @RequestBody Book book,
            @ApiParam(value = "id of the book to update", required = true, example = "1") @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    /**
     * This method find book by isbn param
     * @param isbn: ISBN of the book
     * @return {@link Book}
     * @throws BookNotFoundException when book not found
     */
    @GetMapping("/isbn/{isbn}")
    @ApiOperation(value = "Giving an isbn, return the book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully retrieved book"),
            @ApiResponse(code = 201,message = "Book created successfully"),
            @ApiResponse(code = 404,message = "Book not found")
    })
    public ResponseEntity<Book> findByIsbn(@ApiParam(value = "isbn to find the book", required = true) @PathVariable String isbn) {
        final Book bookLocal = bookRepository.findByIsbn(isbn).orElse(null);

        if(Objects.nonNull(bookLocal)){
            return ResponseEntity.ok(bookLocal);
        }else{
            final Book bookApi = openLibraryService.bookInfo(isbn);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookApi));
        }
    }

}
