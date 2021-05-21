package wolox.training.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import wolox.training.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User userDB;

    @BeforeEach
    public void setUp() {
        userDB = new User();
        userDB.setUserName("jose.delvecchio");
        userDB.setName("Jose");
        userDB.setBirthdate(LocalDate.now().minusYears(26L));
        userDB.setPassword("123");

        userRepository.save(userDB);
    }

    @Test
    public void whenFindByUserName_thenReturnUser() {
        // when
        User userFound = userRepository.findByUserName("jose.delvecchio").orElse(null);

        //then
        assertEquals(userFound.getUserName(),userDB.getUserName());
    }

    @Test
    public void whenFindAll_thenReturnUsers() {
        // when
        List<User> usersFound = userRepository.findAll();

        //then
        assertTrue(usersFound.size() > 0);
    }

    @Test
    public void whenCreateUser_thenReturnUserPersisted(){
        User user = new User();
        user.setUserName("jose.delvecchio2");
        user.setName("Jose2");
        user.setBirthdate(LocalDate.now().minusYears(26L));
        user.setPassword("123");

        User userPersisted = userRepository.save(user);
        assertNotNull(userPersisted.getId());
    }

    @Test
    public void whenCreateUserWithoutData_thenThrowException() {
        //given
        User user = new User();

        //when
        assertThrows(DataIntegrityViolationException.class,() -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenFindByBirthdateBetweenAndName_thenReturnUsers() {
        // when
        List<User> usersFound = userRepository.findByBirthdateBetweenAndNameContainingIgnoreCase(
                LocalDate.now().minusYears(30L),
                LocalDate.now(),"jos");

        //then
        assertTrue(usersFound.size() > 0);
        assertEquals(usersFound.stream().findFirst().get().getUserName(),userDB.getUserName());
    }
}
