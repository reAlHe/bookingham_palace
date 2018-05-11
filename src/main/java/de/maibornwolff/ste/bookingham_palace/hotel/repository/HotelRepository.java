package de.maibornwolff.ste.bookingham_palace.hotel.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

/**
 * Spring Data JPA repository for the Hotel entity.
 */
public interface HotelRepository extends JpaRepository<Hotel, String> {

    Optional<Hotel> findOneByName(String name);

    Optional<Hotel> findOneById(long id);

    List<Hotel> findAllByCity(String city);

    List<Hotel> findAllByContact(User contact);

    boolean existsHotelById(long id);

}
