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
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.HotelNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ForbiddenException;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.system.errors.BadRequestAlertException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ResourceNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.BookingConstants.RESOURCE_BOOKING;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.ErrorConstants.KEY_BOOKING_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.booking.api.constants.ErrorConstants.MSG_BOOKING_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.HotelConstants.RESOURCE_HOTEL;

/**
 * Rest controller for managing bookings.
 */
@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;


    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }


    /**
     * POST /bookings : Creates a new booking
     *
     * @param bookingRequest the booking request
     * @param token          the token to validate the user
     * @return booking response with status 201 (created),
     * or status 401 (unauthorized) when no valid token is provided,
     * or status 400 (bad request) when the provided hotelid within the request does not exist
     */
    @CrossOrigin()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest,
                                        @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        try {
            Booking booking = bookingService.createBooking(bookingMapper.mapBookingRequestToBooking(bookingRequest),
                    Tokens.getUser(token));
            return new ResponseEntity<>(bookingMapper.mapBookingToBookingResponse(booking), HttpStatus.CREATED);
        } catch (HotelNotFoundException e) {
            throw new BadRequestAlertException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
    }


    /**
     * PUT /bookings/bookingId : Updates the given booking
     *
     * @param bookingRequest the booking request
     * @param bookingId the bookingId of the booking to update
     * @param token the token to validate the user
     * @return booking response with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided,
     * or status 400 (bad request) when the provided hotelid within the request does not exist
     */
    @CrossOrigin()
    @RequestMapping(path = "/{bookingId}", method = RequestMethod.PUT)
    public ResponseEntity updateBooking(@RequestBody BookingRequest bookingRequest,
                                        @PathVariable long bookingId,
                                        @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        try {
            Booking booking = bookingService.updateBooking(bookingId,
                    bookingMapper.mapBookingRequestToBooking(bookingRequest),
                    Tokens.getUser(token));
            return new ResponseEntity<>(bookingMapper.mapBookingToBookingResponse(booking), HttpStatus.OK);
        }
        catch (HotelNotFoundException e) {
            throw new BadRequestAlertException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
    }


    /**
     * GET /bookings : Gets all bookings for the user
     *
     * @param token the token to validate the user
     * @return a list with all bookings of the user with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided
     */
    @CrossOrigin()
    @GetMapping
    public ResponseEntity findAllBookingsOfUser(@CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        List<Booking> bookings = bookingService.getAllBookingsOfUser(Tokens.getUser(token));
        return new ResponseEntity<>(bookingMapper.mapBookingsToBookingResponses(bookings), HttpStatus.OK);
    }


    /**
     * GET /bookings/hotel/hotelId : Gets all bookings for the hotel with id
     *
     * @param token the token to validate the user
     * @param hotelId the id of the hotel
     * @return a list with all bookings for the hotel with given id with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided,
     * or status 403 (forbidden) when the user is not allowed to see the hotel bookings
     */
    @CrossOrigin()
    @GetMapping(path = "/hotel/{hotelId}")
    public ResponseEntity findAllBookingsForHotel(@CookieValue(value = "token", required = false) String token,
                                                  @PathVariable long hotelId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        try {
            List<Booking> bookings = bookingService.getAllBookingsOfHotel(hotelId, Tokens.getUser(token));
            return new ResponseEntity<>(bookingMapper.mapBookingsToBookingResponses(bookings), HttpStatus.OK);
        }
        catch (UnauthorizedException e) {
            throw new ForbiddenException(RESOURCE_BOOKING);
        }
    }


    /**
     * DELETE /bookings/bookingId : Deletes the booking with given id
     *
     * @param token the token to validate the user
     * @param bookingId the id of the booking
     * @return status 200 (ok) when the booking could be deleted,
     * or status 401 (unauthorized) when no valid token is provided,
     * or status 404 (resource not found) when no booking with given id exists
     */
    @CrossOrigin()
    @RequestMapping(path = "/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBooking(@CookieValue(value = "token", required = false) String token,
                                        @PathVariable long bookingId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_BOOKING);
        }
        try {
            bookingService.deleteBooking(bookingId,
                    Tokens.getUser(token));
        } catch (BookingNotFoundException e) {
            throw new ResourceNotFoundException(MSG_BOOKING_NOT_FOUND, RESOURCE_BOOKING, KEY_BOOKING_NOT_FOUND);
        }
        catch (UnauthorizedException e) {
            throw new ForbiddenException(RESOURCE_BOOKING);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
