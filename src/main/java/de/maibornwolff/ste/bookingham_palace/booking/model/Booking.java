package de.maibornwolff.ste.bookingham_palace.booking.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Feature;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @JoinColumn(name = "booker", nullable = false)
    @ManyToOne
    private User booker;

    @NotNull
    @JoinColumn(name = "hotel", nullable = false)
    @ManyToOne
    private Hotel hotel;

    @NotNull
    @Column(name = "start_date")
    private Instant startDate;

    @NotNull
    @Column(name = "end_date")
    private Instant endDate;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public User getBooker() {
        return booker;
    }


    public void setBooker(User booker) {
        this.booker = booker;
    }


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
        Booking booking = (Booking) o;
        return id == booking.id &&
                Objects.equal(booker, booking.booker) &&
                Objects.equal(hotel, booking.hotel) &&
                Objects.equal(startDate, booking.startDate) &&
                Objects.equal(endDate, booking.endDate);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, booker, hotel, startDate, endDate);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("booker", booker)
                .add("hotel", hotel)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }
}
