package de.maibornwolff.ste.bookingham_palace.hotel.api;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.ste.bookingham_palace.hotel.api.mapper.HotelMapper;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.hotel.model.HotelRequest;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.HotelNotFoundException;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.system.errors.BadRequestAlertException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ForbiddenException;
import de.maibornwolff.ste.bookingham_palace.system.errors.ResourceNotFoundException;
import de.maibornwolff.ste.bookingham_palace.user.auth.Tokens;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UnauthorizedAlertException;
import de.maibornwolff.ste.bookingham_palace.user.service.errors.UserNotFoundException;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.KEY_USER_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_HOTEL_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.ErrorConstants.MSG_USER_NOT_FOUND;
import static de.maibornwolff.ste.bookingham_palace.hotel.api.constants.HotelConstants.RESOURCE_HOTEL;
import static de.maibornwolff.ste.bookingham_palace.user.api.constants.UserConstants.RESOURCE_USER;

@RestController
@RequestMapping(value = "/hotels")
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;


    public HotelController(HotelService hotelService, HotelMapper hotelMapper) {
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;
    }


    @CrossOrigin()
    @RequestMapping(method = RequestMethod.POST)
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


    @CrossOrigin()
    @RequestMapping(path = "/{hotelId}", method = RequestMethod.PUT)
    public ResponseEntity updateHotel(@RequestBody HotelRequest hotel,
                                      @PathVariable long hotelId,
                                      @CookieValue(value = "token", required = false) String token) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        try {
            Hotel updatedHotel = hotelService.updateHotel(hotelId, hotelMapper.mapHotelRequestToHotel(hotel), Tokens.getUser(token));
            return new ResponseEntity<>(hotelMapper.mapHotelToHotelResponse(updatedHotel), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new BadRequestAlertException(MSG_USER_NOT_FOUND, RESOURCE_USER, KEY_USER_NOT_FOUND);
        }
        catch (HotelNotFoundException e) {
            throw new ResourceNotFoundException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
        catch (UnauthorizedException e) {
            throw new ForbiddenException(RESOURCE_HOTEL);
        }
    }


    @CrossOrigin()
    @GetMapping(params = "city")
    public ResponseEntity findAllHotelsInCity(@CookieValue(value = "token", required = false) String token,
                                              @RequestParam String city) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        List<Hotel> hotels = hotelService.retrieveAllHotelsIn(city);
        return new ResponseEntity<>(hotelMapper.mapHotelsToHotelResponses(hotels), HttpStatus.OK);
    }


    @CrossOrigin()
    @GetMapping(params = {"city", "minimumRating"})
    public ResponseEntity findAllHotelsInCityWithRatingBetterThan(@CookieValue(value = "token", required = false) String token,
                                              @RequestParam String city, @RequestParam int minimumRating) {
        if (!Tokens.verify(token)) {
            throw new UnauthorizedAlertException(RESOURCE_HOTEL);
        }
        List<Hotel> hotels = hotelService.retrieveAllHotelsInCityWithRatingBetterThan(city, minimumRating);
        return new ResponseEntity<>(hotelMapper.mapHotelsToHotelResponses(hotels), HttpStatus.OK);
    }


    @CrossOrigin()
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
            throw new BadRequestAlertException(MSG_HOTEL_NOT_FOUND, RESOURCE_HOTEL, KEY_HOTEL_NOT_FOUND);
        }
    }
}
