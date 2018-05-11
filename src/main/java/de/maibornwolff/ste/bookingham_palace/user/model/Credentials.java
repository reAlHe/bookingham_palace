package de.maibornwolff.ste.bookingham_palace.user.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials implements Serializable {

    @JsonProperty
    private String username;
    @JsonProperty
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credentials() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
