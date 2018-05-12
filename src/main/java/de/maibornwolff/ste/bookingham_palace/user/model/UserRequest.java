package de.maibornwolff.ste.bookingham_palace.user.model;

import java.io.Serializable;

public class UserRequest implements Serializable {


    private String username;

    private String firstName;

    private String lastName;

    private String password;


    public UserRequest(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public UserRequest() {
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
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


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
