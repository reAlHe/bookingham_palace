package de.maibornwolff.ste.bookingham_palace.rating.api.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingCategory;

public class RatingRequest {

    private long hotelId;

    private RatingCategory rating;

    private String comment;

    public RatingRequest(){}


    public long getHotelId() {
        return hotelId;
    }


    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }


    public RatingCategory getRating() {
        return rating;
    }


    public void setRating(RatingCategory rating) {
        this.rating = rating;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RatingRequest that = (RatingRequest) o;
        return hotelId == that.hotelId &&
                Objects.equal(rating, that.rating) &&
                Objects.equal(comment, that.comment);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(hotelId, rating, comment);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hotelId", hotelId)
                .add("rating", rating)
                .add("comment", comment)
                .toString();
    }
}
