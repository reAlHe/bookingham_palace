package de.maibornwolff.ste.bookingham_palace.booking.api.mapper;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.booking.model.Booking;
import de.maibornwolff.ste.bookingham_palace.booking.model.BookingRequest;
import de.maibornwolff.ste.bookingham_palace.booking.model.BookingResponse;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;

/**
 * Mapper to map all the booking related requests and responses
 */
@Service
public class BookingMapper {

    private final HotelService hotelService;

    public BookingMapper(HotelService hotelService){
        this.hotelService = hotelService;
    }


    /**
     * Maps a booking request to the booking entity
     *
     * @param bookingRequest the booking request
     * @return an object of booking
     */
    public Booking mapBookingRequestToBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setHotel(hotelService.findHotelById(bookingRequest.getHotelId()));
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setStartDate(bookingRequest.getStartDate());
        return booking;
    }


    /**
     * Maps a booking to a booking response
     *
     * @param booking a booking
     * @return a booking response from the given booking
     */
    public BookingResponse mapBookingToBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setBooker(booking.getBooker().getUsername());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setHotel(booking.getHotel());
        return bookingResponse;
    }


    /**
     * Maps a list of bookings to booking responses
     *
     * @param bookings the list of bookings
     * @return a list of booking responses
     */
    public List<BookingResponse> mapBookingsToBookingResponses(List<Booking> bookings) {
        return bookings.stream().map(this::mapBookingToBookingResponse).collect(Collectors.toList());
    }
}
