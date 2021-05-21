package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    List<User> findByBirthdateBetweenAndNameContainingIgnoreCase(LocalDate birthdateStart, LocalDate birthdateEnd, String name);

}
