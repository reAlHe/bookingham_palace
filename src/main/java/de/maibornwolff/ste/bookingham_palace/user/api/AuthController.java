package de.maibornwolff.ste.bookingham_palace.user.api;

import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserIsLockedException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.model.Credentials;
import de.maibornwolff.ste.bookingham_palace.user.model.Token;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserNotFoundException;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.KEY_INCORRECT_USER_PASSWORD;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.KEY_USER_IS_LOCKED;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.MSG_INCORRECT_USER_PASSWORD;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.MSG_USER_IS_LOCKED;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.UserConstants.RESOURCE_USER;

/**
 * Rest controller for managing authentications.
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    /**
     * POST /authentication/auth : Authenticates the user
     *
     * @param credentials the credentials
     * @return a token with status 200 (ok),
     * or status 401 (unauthorized) if the credentials are wrong or the user is locked
     */
    @CrossOrigin()
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Token> authenticateUser(@RequestBody Credentials credentials) {
        try {
            Token token = userService.authenticateUser(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (AuthenticationException | UserNotFoundException e) {
            throw new UnauthorizedAlertException(MSG_INCORRECT_USER_PASSWORD, RESOURCE_USER, KEY_INCORRECT_USER_PASSWORD);
        }
        catch (UserIsLockedException e) {
            throw new UnauthorizedAlertException(MSG_USER_IS_LOCKED, RESOURCE_USER, KEY_USER_IS_LOCKED);
        }
    }


    /**
     * POST /authentication/validate : Validates a token
     *
     * @param token the token to validate the user
     * @return status 200 (ok) when the token is valid,
     * or status 403 (forbidden) when the token is not valid
     */
    @CrossOrigin()
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity<Token> validateToken(@RequestBody Token token) {
        if(Tokens.verify(token.getToken())){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    /**
     * POST /authentication/logout : Logs the user out
     *
     * @param token the token to validate the user
     * @return status 200 (ok)
     */
    @CrossOrigin()
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity clearToken(@RequestBody Token token) {
        Tokens.clear(token.getToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
