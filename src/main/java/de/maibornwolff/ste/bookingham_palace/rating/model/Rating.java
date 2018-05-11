package de.maibornwolff.ste.bookingham_palace.rating.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hotel", nullable = false)
    private Hotel hotel;

    @NotNull
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private RatingCategory ratingCategory;

    @NotNull
    @Column(name = "publish_date")
    private Instant publishDate;

    @NotNull
    @JoinColumn(name = "author", nullable = false)
    @ManyToOne
    private User author;

    private String comment;

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public Hotel getHotel() {
        return hotel;
    }


    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public RatingCategory getRatingCategory() {
        return ratingCategory;
    }


    public void setRatingCategory(RatingCategory ratingCategory) {
        this.ratingCategory = ratingCategory;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public User getAuthor() {
        return author;
    }


    public void setAuthor(User author) {
        this.author = author;
    }


    public Instant getPublishDate() {
        return publishDate;
    }


    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rating rating = (Rating) o;
        return id == rating.id &&
                Objects.equal(hotel, rating.hotel) &&
                ratingCategory == rating.ratingCategory &&
                Objects.equal(publishDate, rating.publishDate) &&
                Objects.equal(author, rating.author) &&
                Objects.equal(comment, rating.comment);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, hotel, ratingCategory, publishDate, author, comment);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("hotel", hotel.getId())
                .add("ratingCategory", ratingCategory)
                .add("publishDate", publishDate)
                .add("author", author)
                .add("comment", comment)
                .toString();
    }
}
