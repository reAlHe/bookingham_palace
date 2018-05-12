package de.maibornwolff.ste.bookingham_palace.booking.service;

import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.booking.model.Booking;
import de.maibornwolff.ste.bookingham_palace.booking.repository.BookingRepository;
import de.maibornwolff.ste.bookingham_palace.booking.service.errors.BookingNotFoundException;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;

/**
 * Provides business logic for the entity booking
 */
@Service
public class BookingService {

    private final UserService userService;

    private final HotelService hotelService;

    private final BookingRepository bookingRepository;

    private final Logger log = LoggerFactory.getLogger(BookingService.class);


    public BookingService(UserService userService, HotelService hotelService, BookingRepository bookingRepository) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.bookingRepository = bookingRepository;
    }


    /**
     * Creates a booking
     *
     * @param booking the booking to be created
     * @param username the username of the requesting user
     * @return the created booking
     */
    @Transactional
    public Booking createBooking(Booking booking, String username) {
        log.info("Received request to add booking {}", booking);
        User user = userService.findUserByUsername(username);
        booking.setBooker(user);
        return bookingRepository.saveAndFlush(booking);
    }


    /**
     * Updates a booking
     *
     * @param bookingId the booking id of the booking to be updated
     * @param bookingUpdate the update for the booking
     * @param username the username of the requesting user
     * @return the updated booking or a BookingNotFoundException when the booking does not exist
     */
    @Transactional
    public Booking updateBooking(long bookingId, Booking bookingUpdate, String username) {
        log.info("Received request to update booking {}", bookingUpdate);
        Booking existingBooking = bookingRepository.findOneById(bookingId).orElseThrow(BookingNotFoundException::new);
        User user = userService.findUserByUsername(username);

        if (!isUserAllowedToChangeBooking(existingBooking, user)) {
            throw new UnauthorizedException();
        }

        existingBooking.setBooker(bookingUpdate.getBooker());
        existingBooking.setStartDate(bookingUpdate.getStartDate());
        existingBooking.setEndDate(bookingUpdate.getEndDate());
        existingBooking.setHotel(bookingUpdate.getHotel());
        return bookingRepository.saveAndFlush(existingBooking);
    }


    /**
     * Gets all bookings of the user
     *
     * @param username the username of the requesting user
     * @return a list with all bookings of the user
     */
    @Transactional
    public List<Booking> getAllBookingsOfUser(String username) {
        log.info("Received request to get all bookings of user {}", username);
        User user = userService.findUserByUsername(username);
        return bookingRepository.findAllByBooker(user);
    }


    /**
     * Gets all bookings ofr the given hotel
     *
     * @param hotelId the id of the hotel
     * @param username the username of the requesting user
     * @return a list of all bookings for the hotel
     */
    @Transactional
    public List<Booking> getAllBookingsOfHotel(long hotelId, String username) {
        log.info("Received request to get all bookings of hotel with id {}", hotelId);
        User user = userService.findUserByUsername(username);
        Hotel hotel = hotelService.findHotelById(hotelId);

        if (!isUserAllowedToSeeBookings(hotel, user)) {
            throw new UnauthorizedException();
        }

        return bookingRepository.findAllByHotel(hotel);
    }


    /**
     * Deletes a booking with given id
     *
     * @param bookingId the id of the booking to be deleted
     * @param username the username of the requesting user or a BookingNotFoundException when no booking with given id exists
     */
    @Transactional
    public void deleteBooking(long bookingId, String username) {
        log.info("Received request to delete bookings with id {}", bookingId);
        User user = userService.findUserByUsername(username);
        Booking existingBooking = bookingRepository.findOneById(bookingId).orElseThrow(BookingNotFoundException::new);

        if (!isUserAllowedToDeleteBooking(existingBooking, user)) {
            throw new UnauthorizedException();
        }

        bookingRepository.deleteById(bookingId);
    }


    private boolean isUserAllowedToChangeBooking(Booking existingBooking, User user) {
        return Objects.equals(existingBooking.getBooker(), user);
    }


    private boolean isUserAllowedToDeleteBooking(Booking existingBooking, User user) {
        return Objects.equals(existingBooking.getBooker(), user) || Objects.equals(
                existingBooking.getHotel().getContact(), user);
    }


    private boolean isUserAllowedToSeeBookings(Hotel hotel, User user) {
        return Objects.equals(hotel.getContact(), user);
    }
}
