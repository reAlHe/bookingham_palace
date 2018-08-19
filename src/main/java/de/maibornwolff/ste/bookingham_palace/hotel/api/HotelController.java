package de.maibornwolff.ste.bookingham_palace.hotel.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.hotel.api.dto.HotelRequest;
import de.maibornwolff.ste.bookingham_palace.hotel.api.mapper.HotelMapper;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.HotelNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.BadRequestAlertException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ForbiddenException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ResourceNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.system.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserNotFoundException;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_USER_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_USER_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.HotelConstants.RESOURCE_HOTEL;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.UserConstants.RESOURCE_USER;

/**
 * Rest controller for managing hotels.
 */
@RestController
@RequestMapping(value = "/hotels")
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;


    public HotelController(HotelService hotelService, HotelMapper hotelMapper) {
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;
    }


    /**
     * POST /hotels : Creates a new hotel
     *
     * @param hotel the hotel to be created
     * @param token the token to validate the user
     * @return the created hotel with status 201 (created),
     * or status 400 (bad request) if the contact user does not exist
     * or status 401 (unauthorized) when no valid token is provided,
     */
    @PostMapping
    public ResponseEntity createHotel(@RequestBody HotelRequest hotel,
                                      @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        try {
            Hotel createdHotel = hotelService.createHotel(hotelMapper.mapHotelRequestToHotel(hotel));
            return new ResponseEntity<>(hotelMapper.mapHotelToHotelResponse(createdHotel), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new BadRequestAlertException(MSG_USER_NOT_FOUND, RESOURCE_USER, KEY_USER_NOT_FOUND);
        }
    }


    /**
     * PUT /hotels/hotelId : Updates the hotel with id
     *
     * @param hotel   the hotel update
     * @param hotelId the id of the hotel to be updated
     * @param token   the token to validate the user
     * @return the updated hotel with status 200 (ok),
     * or status 400 (bad request) if the contact user does not exist,
     * or status 401 (unauthorized) when no valid token is provided,
     * or status 403 (forbidden) if the requesting user has no permission to update the hotel
     * or status 404 (resource not found) when no hotel with given id exists
     */
    @PutMapping(path = "/{hotelId}")
    public ResponseEntity updateHotel(@RequestBody HotelRequest hotel,
                                      @PathVariable long hotelId,
                                      @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        try {
            Hotel updatedHotel = hotelService.updateHotel(hotelId, hotelMapper.mapHotelRequestToHotel(hotel),
                    Tokens.getUser(token));
            return new ResponseEntity<>(hotelMapper.mapHotelToHotelResponse(updatedHotel), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new BadRequestAlertException(MSG_USER_NOT_FOUND, RESOURCE_USER, KEY_USER_NOT_FOUND);
        } catch (HotelNotFoundException e) {
            throw new ResourceNotFoundException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        } catch (UnauthorizedException e) {
            throw new ForbiddenException(RESOURCE_HOTEL);
        }
    }


    /**
     * GET /hotels?city=city : Gets a list with all hotels in the given city
     *
     * @param token the token to validate the user
     * @param city  the city to search for
     * @return a list with all hotels in the city with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided
     */
    @GetMapping(params = "city")
    public ResponseEntity findAllHotelsInCity(@CookieValue(value = "token", required = false) String token,
                                              @RequestParam String city) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        List<Hotel> hotels = hotelService.retrieveAllHotelsIn(city);
        return new ResponseEntity<>(hotelMapper.mapHotelsToHotelResponses(hotels), HttpStatus.OK);
    }


    /**
     * GET /hotels?city=city&minimumRating=minimumRating : Gets a list with all hotels in a city that have an average rating better than a minimum
     *
     * @param token         the token to validate the user
     * @param city          the city to search for
     * @param minimumRating the minimum rating for a hotel to be listed
     * @return a list with all hotels in the given city that have an average rating better than a given minimum with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided
     */
    @GetMapping(params = {"city", "minimumRating"})
    public ResponseEntity findAllHotelsInCityWithRatingBetterThan(
            @CookieValue(value = "token", required = false) String token,
            @RequestParam String city, @RequestParam int minimumRating) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        List<Hotel> hotels = hotelService.retrieveAllHotelsInCityWithRatingBetterThan(city, minimumRating);
        return new ResponseEntity<>(hotelMapper.mapHotelsToHotelResponses(hotels), HttpStatus.OK);
    }


    /**
     * GET /hotels/rating/hotelId : Gets the average rating for a given hotel
     *
     * @param token   the token to validate the user
     * @param hotelId the id of the hotel
     * @return the average rating for the given hotel with status 200 (ok),
     * or status 401 (unauthorized) when no valid token is provided
     */
    @GetMapping(path = "/rating/{hotelId}")
    public ResponseEntity getAverageRatingForHotel(@CookieValue(value = "token", required = false) String token,
                                                   @PathVariable long hotelId) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        try {
            double rating = hotelService.getHotelAverageRating(hotelId);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } catch (HotelNotFoundException e) {
            throw new ResourceNotFoundException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
    }
}
