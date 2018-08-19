package de.maibornwolff.ste.bookingham_palace.hotel.api.dto;

import java.util.List;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Feature;
import de.maibornwolff.ste.bookingham_palace.rating.api.dto.RatingResponse;

public class HotelResponse {

    private long id;

    private String name;

    private String street;

    private String zipcode;

    private String city;

    private String contact;

    private List<RatingResponse> ratings;

    private List<Feature> features;


    public HotelResponse() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getStreet() {
        return street;
    }


    public void setStreet(String street) {
        this.street = street;
    }


    public String getZipcode() {
        return zipcode;
    }


    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getContact() {
        return contact;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public List<Feature> getFeatures() {
        return features;
    }


    public void setFeatures(List<Feature> features) {
        this.features = features;
    }


    public List<RatingResponse> getRatings() {
        return ratings;
    }


    public void setRatings(List<RatingResponse> ratings) {
        this.ratings = ratings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelResponse that = (HotelResponse) o;
        return id == that.id &&
                Objects.equal(name, that.name) &&
                Objects.equal(street, that.street) &&
                Objects.equal(zipcode, that.zipcode) &&
                Objects.equal(city, that.city) &&
                Objects.equal(contact, that.contact) &&
                Objects.equal(ratings, that.ratings) &&
                Objects.equal(features, that.features);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, street, zipcode, city, contact, ratings, features);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("street", street)
                .add("zipcode", zipcode)
                .add("city", city)
                .add("contact", contact)
                .add("ratings", ratings)
                .add("features", features)
                .toString();
    }
}
