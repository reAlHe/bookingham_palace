package de.maibornwolff.ste.bookingham_palace.rating.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

/**
 * Spring Data JPA repository for the Rating entity.
 */
public interface RatingRepository extends JpaRepository<Rating, String> {

    List<Rating> findAllByAuthor(User author);

    Optional<Rating> findOneById(long id);

    void deleteById(long id);
}
