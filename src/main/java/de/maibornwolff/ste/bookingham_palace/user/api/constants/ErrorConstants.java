package de.maibornwolff.ste.bookingham_palace.user.api.constants;

public final class ErrorConstants {

    public static final String KEY_USER_ALREADY_REGISTERED = "username_already_in_use";
    public static final String KEY_INCORRECT_USER_PASSWORD = "incorrect_username_or_password";
    public static final String KEY_USER_IS_LOCKED = "account_is_locked";
    public static final String KEY_UNAUTHORIZED = "user_not_authorized";

    public static final String MSG_USER_ALREADY_REGISTERED = "The username is already in use";
    public static final String MSG_INCORRECT_USER_PASSWORD = "Either username or password are incorrect";
    public static final String MSG_USER_IS_LOCKED = "The account has been locked due to too many failed logins";
    public static final String MSG_UNAUTHORIZED = "User is not logged in";

    private ErrorConstants() {
    }

}
