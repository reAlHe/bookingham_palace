package de.maibornwolff.ste.bookingham_palace.user.api.mapper;


import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.api.dto.UserRequest;
import de.maibornwolff.ste.bookingham_palace.user.api.dto.UserResponse;

/**
 * Mapper to map all the booking related requests and responses
 */
@Service
public class UserMapper {


    /**
     * Maps a user request to the user entity
     *
     * @param userRequest a user request
     * @return an object of user
     */
    public User mapUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setLocked(false);
        return user;
    }


    /**
     * Maps a user to an user response
     *
     * @param user a user
     * @return a user response from the given user
     */
    public UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        user.setFailedLogins(0);
        return userResponse;
    }
}
