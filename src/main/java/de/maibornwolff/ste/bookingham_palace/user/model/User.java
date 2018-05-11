package de.maibornwolff.ste.bookingham_palace.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(length = 100, unique = true)
    private String username;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name", length = 50)
    private String lastName;

    @JsonIgnore
    @NotNull
    @Size(min = 5, max = 15)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean locked;

    @NotNull
    @Column(name = "failed_logins")
    private int failedLogins;


    public User() {
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean isLocked() {
        return locked;
    }


    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    public int getFailedLogins() {
        return failedLogins;
    }


    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                locked == user.locked &&
                failedLogins == user.failedLogins &&
                Objects.equal(username, user.username) &&
                Objects.equal(firstName, user.firstName) &&
                Objects.equal(lastName, user.lastName) &&
                Objects.equal(password, user.password);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, firstName, lastName, password, locked, failedLogins);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("password", password)
                .add("locked", locked)
                .add("failedLogins", failedLogins)
                .toString();
    }
}
