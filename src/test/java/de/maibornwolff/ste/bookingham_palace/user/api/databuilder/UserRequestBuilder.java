package de.maibornwolff.ste.bookingham_palace.user.api.databuilder;

import de.maibornwolff.ste.bookingham_palace.user.model.UserRequest;

public class UserRequestBuilder {

    private String username = "princeHarry";
    private String firstName = "Harry";
    private String lastName = "Wales";
    private String password = "meggie123";

    public UserRequestBuilder(){
    }

    public UserRequestBuilder withUsername(String userName) {
        this.username = userName;
        return this;
    }

    public UserRequestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRequestBuilder withLastName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRequest build() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setPassword(password);
        return userRequest;
    }

}
