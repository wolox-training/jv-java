package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    @Query("SELECT u "
            + " FROM User u "
            + " WHERE (:birthdateStart is null or u.birthdate between :birthdateStart and :birthdateEnd)"
            + " AND (:name is null or UPPER(u.name) like UPPER(concat('%', :name,'%')))")
    List<User> findByBirthdateBetweenAndNameContainingIgnoreCase(@Param("birthdateStart") LocalDate birthdateStart,
            @Param("birthdateEnd") LocalDate birthdateEnd, @Param("name") String name);
}
