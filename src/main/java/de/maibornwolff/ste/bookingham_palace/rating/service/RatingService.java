package de.maibornwolff.ste.bookingham_palace.rating.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingCategory;
import de.maibornwolff.ste.bookingham_palace.rating.repository.RatingRepository;
import de.maibornwolff.ste.bookingham_palace.rating.service.errors.RatingNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;

@Service
public class RatingService {

    private final UserService userService;

    private final RatingRepository ratingRepository;

    private final Logger log = LoggerFactory.getLogger(RatingService.class);


    public RatingService(UserService userService, RatingRepository ratingRepository) {
        this.userService = userService;
        this.ratingRepository = ratingRepository;
    }

    public Double calculateHotelAverageRating(List<Rating> ratings) {
        return ratings.stream().map(Rating::getRatingCategory).mapToInt(RatingCategory::getPoints).average().orElse(Double.NaN);
    }


    @Transactional
    public void createRating(Rating rating, String authorname) {
        log.info("Received request to add rating {}", rating);
        User author = userService.findUserByUsername(authorname);
        rating.setAuthor(author);
        rating.setPublishDate(Instant.now());
        ratingRepository.saveAndFlush(rating);
    }


    @Transactional
    public List<Rating> retrieveAllRatingsOfUser(String username) {
        log.info("Received request to get all ratings of user {}", username);
        User user = userService.findUserByUsername(username);
        return ratingRepository.findAllByAuthor(user);
    }


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
