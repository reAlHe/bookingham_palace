package de.maibornwolff.ste.bookingham_palace.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByUsername(String username);

    boolean existsByUsername(String username);

}
