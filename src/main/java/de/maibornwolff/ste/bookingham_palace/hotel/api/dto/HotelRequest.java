package de.maibornwolff.ste.bookingham_palace.hotel.api.dto;

import java.util.List;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Feature;

public class HotelRequest {

    private String name;

    private String street;

    private String zipcode;

    private String city;

    private String contact;

    private List<Feature> features;


    public HotelRequest() {
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


    public List<Feature> getFeatures() {
        return features;
    }


    public void setFeatures(List<Feature> features) {
        this.features = features;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelRequest that = (HotelRequest) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(street, that.street) &&
                Objects.equal(zipcode, that.zipcode) &&
                Objects.equal(city, that.city) &&
                Objects.equal(contact, that.contact) &&
                Objects.equal(features, that.features);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(name, street, zipcode, city, contact, features);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("street", street)
                .add("zipcode", zipcode)
                .add("city", city)
                .add("contact", contact)
                .add("features", features)
                .toString();
    }
}
