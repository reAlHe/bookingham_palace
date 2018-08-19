package de.maibornwolff.ste.bookingham_palace.booking.api.dto;

import java.time.LocalDate;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class BookingRequest {

    private long hotelId;

    private LocalDate startDate;

    private LocalDate endDate;


    public long getHotelId() {
        return hotelId;
    }


    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
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
