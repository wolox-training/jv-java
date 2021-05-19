package wolox.training.repositories;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User userDB;

    @BeforeEach
    public void setUp() {
        userDB = new User();
        userDB.setUserName("jose.delvecchio");
        userDB.setName("Jose");
        userDB.setBirthdate(LocalDate.now());

        entityManager.persist(userDB);
        entityManager.flush();
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

        User userPersisted = entityManager.persist(user);
        entityManager.flush();
        assertNotNull(userPersisted.getId());
    }

    @Test
    public void whenCreateUserWithoutData_thenThrowException() {
        //given
        User user = new User();

        //when
        assertThrows(PersistenceException.class,() -> {
            entityManager.persist(user);
            entityManager.flush();
        });
    }
}
