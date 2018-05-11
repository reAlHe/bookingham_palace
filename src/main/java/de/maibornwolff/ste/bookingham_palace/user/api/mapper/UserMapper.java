package de.maibornwolff.ste.bookingham_palace.user.api.mapper;


import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.model.UserRequest;
import de.maibornwolff.ste.bookingham_palace.user.model.UserResponse;

@Service
public class UserMapper {

    public User mapUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setLocked(false);
        return user;
    }

    public UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        user.setFailedLogins(0);
        return userResponse;
    }
}
