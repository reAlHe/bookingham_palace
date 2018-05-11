package de.maibornwolff.ste.bookingham_palace.booking.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.booking.api.mapper.BookingMapper;
import de.maibornwolff.ste.bookingham_palace.booking.model.Booking;
import de.maibornwolff.ste.bookingham_palace.booking.model.BookingRequest;
import de.maibornwolff.ste.bookingham_palace.booking.service.BookingService;
import de.maibornwolff.ste.bookingham_palace.booking.service.errors.BookingNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ResourceNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.BookingConstants.RESOURCE_BOOKING;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.ErrorConstants.KEY_BOOKING_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.ErrorConstants.MSG_BOOKING_NOT_FOUND;

@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;


    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }


    @CrossOrigin()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest,
                                        @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        Booking booking = bookingService.createBooking(bookingMapper.mapBookingRequestToBooking(bookingRequest),
                Tokens.getUser(token));
        return new ResponseEntity<>(bookingMapper.mapBookingToBookingResponse(booking), HttpStatus.CREATED);
    }


    @CrossOrigin()
    @RequestMapping(path = "/{bookingId}", method = RequestMethod.POST)
    public ResponseEntity updateBooking(@RequestBody BookingRequest bookingRequest,
                                        @PathVariable long bookingId,
                                        @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        Booking booking = bookingService.updateBooking(bookingId,
                bookingMapper.mapBookingRequestToBooking(bookingRequest),
                Tokens.getUser(token));
        return new ResponseEntity<>(bookingMapper.mapBookingToBookingResponse(booking), HttpStatus.CREATED);
    }


    @CrossOrigin()
    @GetMapping
    public ResponseEntity findAllBookingsOfUser(@CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        List<Booking> bookings = bookingService.getAllBookingsOfUser(Tokens.getUser(token));
        return new ResponseEntity<>(bookingMapper.mapBookingsToBookingResponses(bookings), HttpStatus.OK);
    }


    @CrossOrigin()
    @GetMapping(path = "/hotel/{hotelId}")
    public ResponseEntity findAllBookingsForHotel(@CookieValue(value = "token", required = false) String token,
                                                  @PathVariable long hotelId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        List<Booking> bookings = bookingService.getAllBookingsOfHotel(hotelId, Tokens.getUser(token));
        return new ResponseEntity<>(bookingMapper.mapBookingsToBookingResponses(bookings), HttpStatus.OK);
    }


    @CrossOrigin()
    @RequestMapping(path = "/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBooking(@RequestBody BookingRequest bookingRequest,
                                        @CookieValue(value = "token", required = false) String token,
                                        @PathVariable long hotelId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        try {
            bookingService.deleteBooking(hotelId,
                    Tokens.getUser(token));
        }
        catch (BookingNotFoundException e) {
            throw new ResourceNotFoundException(MSG_BOOKING_NOT_FOUND, RESOURCE_BOOKING, KEY_BOOKING_NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
