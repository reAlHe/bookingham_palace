package de.maibornwolff.ste.bookingham_palace.booking.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import de.maibornwolff.ste.bookingham_palace.booking.model.Booking;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

/**
 * Spring Data JPA repository for the Booking entity.
 */
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<Booking> findOneById(long id);

    List<Booking> findAllByHotel(Hotel hotel);

    List<Booking> findAllByBooker(User booker);

    void deleteById(long id);
}
