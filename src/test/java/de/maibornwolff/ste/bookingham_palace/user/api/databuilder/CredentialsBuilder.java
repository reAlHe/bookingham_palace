package de.maibornwolff.ste.bookingham_palace.user.api.databuilder;

import de.maibornwolff.ste.bookingham_palace.user.model.Credentials;

public class CredentialsBuilder {

    private String username = "princeHarry";
    private String password = "meggie123";

    public CredentialsBuilder(){
    }

    public CredentialsBuilder withUsername(String userName) {
        this.username = userName;
        return this;
    }

    public CredentialsBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public Credentials build() {
        Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        return credentials;
    }

}
