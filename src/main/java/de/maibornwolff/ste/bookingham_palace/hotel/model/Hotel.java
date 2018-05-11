package de.maibornwolff.ste.bookingham_palace.hotel.model;

import java.time.Instant;
import java.util.ArrayList;
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
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String street;

    @NotNull
    @Column(name = "zip_code")
    private String zipcode;

    @NotNull
    private String city;

    @NotNull
    @Column(name = "registered_since")
    private Instant registeredSince;

    @NotNull
    @JoinColumn(name = "contact", nullable = false)
    @ManyToOne
    private User contact;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "hotel")
    private List<Rating> ratings = new ArrayList<>();


    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Feature.class)
    private List<Feature> features = new ArrayList<>();


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Instant getRegisteredSince() {
        return registeredSince;
    }


    public void setRegisteredSince(Instant registeredSince) {
        this.registeredSince = registeredSince;
    }


    public User getContact() {
        return contact;
    }


    public void setContact(User contact) {
        this.contact = contact;
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


    public List<Rating> getRatings() {
        return ratings;
    }


    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
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
        Hotel hotel = (Hotel) o;
        return id == hotel.id &&
                Objects.equal(name, hotel.name) &&
                Objects.equal(street, hotel.street) &&
                Objects.equal(zipcode, hotel.zipcode) &&
                Objects.equal(city, hotel.city) &&
                Objects.equal(registeredSince, hotel.registeredSince) &&
                Objects.equal(contact, hotel.contact) &&
                Objects.equal(ratings, hotel.ratings) &&
                Objects.equal(features, hotel.features);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, street, zipcode, city, registeredSince, contact, ratings, features);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("street", street)
                .add("zipcode", zipcode)
                .add("city", city)
                .add("registeredSince", registeredSince)
                .add("contact", contact)
                .add("ratings", ratings)
                .add("features", features)
                .toString();
    }
}
