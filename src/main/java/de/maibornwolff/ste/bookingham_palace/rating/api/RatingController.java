package de.maibornwolff.ste.bookingham_palace.rating.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.rating.api.mapper.RatingMapper;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.HotelNotFoundException;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingRequest;
import de.maibornwolff.ste.bookingham_palace.rating.service.RatingService;
import de.maibornwolff.ste.bookingham_palace.rating.service.errors.RatingNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.BadRequestAlertException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ForbiddenException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ResourceNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.HotelConstants.RESOURCE_HOTEL;
import static de.maibornwolff.ste.bookingham_palace.rating.api.constants.ErrorConstants.KEY_RATING_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.rating.api.constants.ErrorConstants.MSG_RATING_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.rating.api.constants.RatingConstants.RESOURCE_RATING;

/**
 * Rest controller for managing ratings.
 */
@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    private final RatingService ratingService;

    private final RatingMapper ratingMapper;


    public RatingController(RatingService ratingService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
    }


    /**
     * POST /ratings : Creates a rating for a hotel
     *
     * @param token the token to validate the user
     * @param rating the rating
     * @return the created rating with status 201 (created),
     * or status 400 (bad request) when the corresponding hotel does not exist
     * or status 401 (unauthorized) when no valid token is provided
     */
    @CrossOrigin()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addRatingToHotel(@CookieValue(value = "token", required = false) String token,
                                           @RequestBody RatingRequest rating) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_RATING);
        }
        try {
            Rating createdRating = ratingService.createRating(ratingMapper.ratingRequestToRating(rating), Tokens.getUser(token));
            return new ResponseEntity<>(ratingMapper.ratingToRatingResponse(createdRating), HttpStatus.OK);
        } catch (HotelNotFoundException e) {
            throw new BadRequestAlertException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
    }


    /**
     * DELETE /ratings/ratingId : Deletes a rating
     *
     * @param token he token to validate the user
     * @param ratingId the id of the rating to be deleted
     * @return status 200 (ok) when the rating could be deleted
     */
    @CrossOrigin()
    @RequestMapping(path = "/{ratingId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRatingForHotel(@CookieValue(value = "token", required = false) String token,
                                           @PathVariable long ratingId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_RATING);
        }
        try {
            ratingService.deleteRating(ratingId, Tokens.getUser(token));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RatingNotFoundException e) {
            throw new ResourceNotFoundException(MSG_RATING_NOT_FOUND, RESOURCE_RATING, KEY_RATING_NOT_FOUND);
        } catch (UnauthorizedException e) {
            throw new ForbiddenException(RESOURCE_RATING);
        }
    }


    @CrossOrigin()
    @GetMapping
    public ResponseEntity getAllRatingsOfUser(@CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_RATING);
        }
        List<Rating> ratings = ratingService.retrieveAllRatingsOfUser(Tokens.getUser(token));
        return new ResponseEntity<>(ratingMapper.ratingsToRatingResponses(ratings), HttpStatus.OK);
    }

}
