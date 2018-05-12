package de.maibornwolff.ste.bookingham_palace.user.service;

import java.util.Objects;
import javax.naming.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserIsLockedException;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.model.Credentials;
import de.maibornwolff.ste.bookingham_palace.user.model.Token;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.repository.UserRepository;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UsernameAlreadyInUseException;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.UserConstants.MAXIMAL_FAILED_LOGINS;


/**
 * Provides business logic for the entity user
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Creates a new user
     *
     * @param user the user to be created
     * @return the created user or a UsernameAlreadyInUseException when the username is already in use
     */
    @Transactional
    public User createUser(User user) {
        log.info("Received request for creating user {}", user);
        if (isUsernameInUse(user.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }
        return userRepository.saveAndFlush(user);
    }


    /**
     * Authenticates the user
     *
     * @param credentials the credentials
     * @return a token to identify the user
     * @throws AuthenticationException is thrown when the credentials does not identify a user
     */
    public Token authenticateUser(Credentials credentials) throws AuthenticationException {
        log.info("Received request for authenticating user with credentials {}", credentials);
        User user = findUserByUsername(credentials.getUsername());
        if (user.isLocked()) {
            throw new UserIsLockedException();
        }
        if (!Objects.equals(user.getPassword(), credentials.getPassword())) {
            processFailedLogin(user);
            throw new AuthenticationException();
        }
        clearFailedLogins(user);
        return new Token(Tokens.create(credentials.getUsername()));
    }


    /**
     * Determines whether a username is already in use
     *
     * @param username a username
     * @return true if the username already exists, else false
     */
    @Transactional
    public boolean isUsernameInUse(String username) {
        return userRepository.existsByUsername(username);
    }


    /**
     * Increments the number of failed logins for the user
     *
     * @param user a user
     * @return the number of failed logins
     */
    @Transactional
    public int incrementFailedLogins(User user) {
        user.setFailedLogins(user.getFailedLogins() + 1);
        userRepository.saveAndFlush(user);
        return user.getFailedLogins();
    }


    /**
     * Locks a user
     *
     * @param user a user
     */
    @Transactional
    public void lockUser(User user) {
        user.setLocked(true);
        userRepository.saveAndFlush(user);
    }


    /**
     * Clears the number of failed logins of a user
     *
     * @param user a user
     */
    @Transactional
    public void clearFailedLogins(User user) {
        user.setFailedLogins(0);
        userRepository.saveAndFlush(user);
    }


    /**
     * Finds a user in the repository by its username
     *
     * @param username a username
     * @return a user with given username or a UserNotFoundException if no user with given username exists
     */
    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findOneByUsername(username).orElseThrow(UserNotFoundException::new);
    }


    private void processFailedLogin(User user) {
        int numberOfFailedLogins = incrementFailedLogins(user);
        if (numberOfFailedLogins >= MAXIMAL_FAILED_LOGINS) {
            lockUser(user);
        }
    }


}
