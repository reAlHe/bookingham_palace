package de.maibornwolff.ste.bookingham_palace.booking.model;

import java.time.Instant;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;

public class BookingRequest {

    private Hotel hotel;

    private Instant startDate;

    private Instant endDate;


    public Hotel getHotel() {
        return hotel;
    }


    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public Instant getStartDate() {
        return startDate;
    }


    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }


    public Instant getEndDate() {
        return endDate;
    }


    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingRequest that = (BookingRequest) o;
        return Objects.equal(hotel, that.hotel) &&
                Objects.equal(startDate, that.startDate) &&
                Objects.equal(endDate, that.endDate);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(hotel, startDate, endDate);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hotel", hotel)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }
}
