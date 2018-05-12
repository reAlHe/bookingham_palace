package de.maibornwolff.ste.bookingham_palace.rating.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingCategory;
import de.maibornwolff.ste.bookingham_palace.rating.repository.RatingRepository;
import de.maibornwolff.ste.bookingham_palace.rating.service.errors.RatingNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;

/**
 * Provides business logic for the entity rating
 */
@Service
public class RatingService {

    private final UserService userService;

    private final RatingRepository ratingRepository;

    private final Logger log = LoggerFactory.getLogger(RatingService.class);


    public RatingService(UserService userService, RatingRepository ratingRepository) {
        this.userService = userService;
        this.ratingRepository = ratingRepository;
    }


    /**
     * Calculates the average rating of a hotel
     *
     * @param ratings the list of ratings
     * @return the average rating of a hotel
     */
    public Double calculateHotelAverageRating(List<Rating> ratings) {
        return ratings.stream().map(Rating::getRatingCategory).mapToInt(RatingCategory::getPoints).average().orElse(
                Double.NaN);
    }


    /**
     * Creates a rating
     *
     * @param rating     the rating to be created
     * @param authorname the username of the requesting user
     * @return the created rating
     */
    @Transactional
    public Rating createRating(Rating rating, String authorname) {
        log.info("Received request to add rating {}", rating);
        User author = userService.findUserByUsername(authorname);
        rating.setAuthor(author);
        rating.setPublishDate(Instant.now());
        return ratingRepository.saveAndFlush(rating);
    }


    /**
     * Retrieves all ratings for the user
     *
     * @param username the username of the requesting user
     * @return a list with all ratings of the user
     */
    @Transactional
    public List<Rating> retrieveAllRatingsOfUser(String username) {
        log.info("Received request to get all ratings of user {}", username);
        User user = userService.findUserByUsername(username);
        return ratingRepository.findAllByAuthor(user);
    }


    /**
     * Deletes a rating with given id
     *
     * @param ratingId the rating id
     * @param username the username of the requesting user
     */
    @Transactional
    public void deleteRating(long ratingId, String username) {
        log.info("Received request to delete rating with id {}", ratingId);
        User user = userService.findUserByUsername(username);
        Rating rating = ratingRepository.findOneById(ratingId).orElseThrow(RatingNotFoundException::new);
        if (!isUserAllowedToDeleteRating(rating, user)) {
            throw new UnauthorizedException();
        }
        ratingRepository.deleteById(ratingId);
    }


    private boolean isUserAllowedToDeleteRating(Rating rating, User user) {
        return Objects.equals(rating.getAuthor(), user);
    }
}
