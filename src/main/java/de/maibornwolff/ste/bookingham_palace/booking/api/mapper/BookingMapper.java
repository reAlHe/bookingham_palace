package de.maibornwolff.ste.bookingham_palace.booking.api.mapper;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.booking.model.Booking;
import de.maibornwolff.ste.bookingham_palace.booking.model.BookingRequest;
import de.maibornwolff.ste.bookingham_palace.booking.model.BookingResponse;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.model.UserRequest;
import de.maibornwolff.ste.bookingham_palace.user.model.UserResponse;

@Service
public class BookingMapper {

    public Booking mapBookingRequestToBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setHotel(bookingRequest.getHotel());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setStartDate(bookingRequest.getStartDate());
        return booking;
    }


    public BookingResponse mapBookingToBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setBooker(booking.getBooker().getUsername());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setHotel(booking.getHotel());
        return bookingResponse;
    }


    public List<BookingResponse> mapBookingsToBookingResponses(List<Booking> bookings) {
        return bookings.stream().map(this::mapBookingToBookingResponse).collect(Collectors.toList());
    }
}
