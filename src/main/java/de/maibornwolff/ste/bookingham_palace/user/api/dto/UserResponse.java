package de.maibornwolff.ste.bookingham_palace.user.api.dto;

import java.io.Serializable;
import de.maibornwolff.ste.bookingham_palace.user.model.User;

public class UserResponse implements Serializable {

    private String username;

    private String firstName;

    private String lastName;

    public UserResponse() {
    }

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.username = user.getUsername();
        userResponse.firstName = user.getFirstName();
        userResponse.lastName = user.getLastName();
        return userResponse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
