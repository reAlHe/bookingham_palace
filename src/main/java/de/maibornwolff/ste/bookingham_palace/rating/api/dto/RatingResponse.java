package de.maibornwolff.ste.bookingham_palace.rating.api.dto;

import java.time.Instant;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingCategory;

public class RatingResponse {

    private long id;

    private String hotel;

    private RatingCategory ratingCategory;

    private String comment;

    private Instant publishDate;

    private String author;

    public RatingResponse(){}


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getHotel() {
        return hotel;
    }


    public void setHotel(String hotel) {
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


    public Instant getPublishDate() {
        return publishDate;
    }


    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RatingResponse that = (RatingResponse) o;
        return id == that.id &&
                Objects.equal(hotel, that.hotel) &&
                ratingCategory == that.ratingCategory &&
                Objects.equal(comment, that.comment) &&
                Objects.equal(publishDate, that.publishDate) &&
                Objects.equal(author, that.author);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, hotel, ratingCategory, comment, publishDate, author);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("hotel", hotel)
                .add("ratingCategory", ratingCategory)
                .add("comment", comment)
                .add("publishDate", publishDate)
                .add("author", author)
                .toString();
    }
}
