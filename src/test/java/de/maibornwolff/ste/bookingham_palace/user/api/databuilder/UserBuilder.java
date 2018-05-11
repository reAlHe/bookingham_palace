package de.maibornwolff.ste.bookingham_palace.user.api.databuilder;

import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.model.UserRequest;

public class UserBuilder {

    private String username = "princeHarry";
    private String firstName = "Harry";
    private String lastName = "Wales";
    private String password = "meggie123";
    private boolean locked = false;

    public UserBuilder(){
    }

    public UserBuilder withUsername(String userName) {
        this.username = userName;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setLocked(locked);
        return user;
    }

}
