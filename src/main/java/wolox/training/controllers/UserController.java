package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

/**
 * @author josefranciscodelvecchio
 * Controller for CRUD User
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * This method find all users
     * @return {@link List}<{@link User}>
     */
    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * This method find users that match with title param
     * @param userName: Title of the User
     * @return {@link User}
     */
    @GetMapping("/user-name/{userName}")
    public User findByUserName(@PathVariable String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * This method find user by id param
     * @param id: Id of the user
     * @return {@link User}
     * @throws UserNotFoundException when user not found
     */
    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * This method creates an {@link User} with the data from user param
     * @param user {@link User}: Data to create
     * @return created {@link User}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * This method delete user by id param
     * @param id: Id of the user
     * @throws UserNotFoundException when user to delete not found
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    /**
     * This method update data an user
     * @param user
     * @param id: Id of the user
     * @return {@link User}: Data to update
     * @throws UserIdMismatchException when id param and user's id mismatch
     * @throws UserNotFoundException when user not found
     */
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    /**
     * This method add an {@link Book} to user
     * @param id: id of the user
     * @param bookId: book id to add
     * @return {@link User}
     * @throws UserNotFoundException when user not found
     * @throws BookNotFoundException when book to add not found
     */
    @PostMapping("/{id}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public User addBook(@PathVariable Long id, @PathVariable Long bookId) {
        final User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        final Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        user.addBook(book);

        return userRepository.save(user);
    }

    /**
     * This method remove a book from user
     * @param id: id of the user
     * @param bookId: book id to delete
     * @throws UserNotFoundException when user not found
     * @throws BookNotFoundException when book to remove not found
     */
    @DeleteMapping("/{id}/books/{bookId}")
    public void removeBook(@PathVariable Long id, @PathVariable Long bookId) {
        final User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        final Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        user.removeBook(book);

        userRepository.save(user);
    }
}
