package de.maibornwolff.ste.bookingham_palace.booking.model;

import java.time.Instant;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class BookingRequest {

    private long hotelId;

    private Instant startDate;

    private Instant endDate;


    public long getHotelId() {
        return hotelId;
    }


    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
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
        return Objects.equal(hotelId, that.hotelId) &&
                Objects.equal(startDate, that.startDate) &&
                Objects.equal(endDate, that.endDate);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(hotelId, startDate, endDate);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hotelId", hotelId)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }
}
