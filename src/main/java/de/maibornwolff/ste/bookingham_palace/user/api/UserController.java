package de.maibornwolff.ste.bookingham_palace.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.system.errors.BadRequestAlertException;
import de.maibornwolff.ste.bookingham_palace.user.api.mapper.UserMapper;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.api.dto.UserRequest;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UsernameAlreadyInUseException;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.KEY_USER_ALREADY_REGISTERED;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.ErrorConstants.MSG_USER_ALREADY_REGISTERED;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.UserConstants.RESOURCE_USER;

/**
 * Rest controller for managing users.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    /**
     * POST /users : Creates a new user
     *
     * @param userRequest the user to be created
     * @return the created user with status 201 (created),
     * or status 400 (bad request) when the username already exists
     */
    @CrossOrigin()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.createUser(userMapper.mapUserRequestToUser(userRequest));
            return new ResponseEntity<>(userMapper.mapUserToUserResponse(user), HttpStatus.CREATED);
        }
        catch (UsernameAlreadyInUseException e) {
            throw new BadRequestAlertException(MSG_USER_ALREADY_REGISTERED, RESOURCE_USER, KEY_USER_ALREADY_REGISTERED);
        }
    }

}
