package de.maibornwolff.ste.bookingham_palace.hotel.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.hotel.repository.HotelRepository;
import de.maibornwolff.ste.bookingham_palace.hotel.service.errors.HotelNotFoundException;
import de.maibornwolff.ste.bookingham_palace.system.errors.UnauthorizedException;
import de.maibornwolff.ste.bookingham_palace.rating.service.RatingService;
import de.maibornwolff.ste.bookingham_palace.user.model.User;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;

/**
 * Provides business logic for the entity hotel
 */
@Service
public class HotelService {

    private final UserService userService;

    private final RatingService ratingService;

    private final HotelRepository hotelRepository;

    private final Logger log = LoggerFactory.getLogger(HotelService.class);


    public HotelService(UserService userService, RatingService ratingService, HotelRepository hotelRepository) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.hotelRepository = hotelRepository;
    }


    /**
     * Creates a hotel
     *
     * @param hotel the hotel to be created
     * @return the created hotel
     */
    @Transactional
    public Hotel createHotel(Hotel hotel) {
        log.info("Received request for creating hotel {}", hotel);
        hotel.setRegisteredSince(Instant.now());
        return hotelRepository.saveAndFlush(hotel);
    }


    /**
     * Updates a given hotel
     *
     * @param hotelId the id of the hotel to be updated
     * @param hotelUpdate the hotel update
     * @param username the username of the requesting user
     * @return the updated hotel or a HotelNotFoundException when not hotel with given id exist
     */
    @Transactional
    public Hotel updateHotel(long hotelId, Hotel hotelUpdate, String username) {
        log.info("Received request for updating hotel with id {} by values {}", hotelId, hotelUpdate);

        Hotel existingHotel = hotelRepository.findOneById(hotelId).orElseThrow(HotelNotFoundException::new);
        User user = userService.findUserByUsername(username);

        if (!isUserAllowedToUpdateHotel(existingHotel, user)) {
            throw new UnauthorizedException();
        }

        existingHotel.setName(hotelUpdate.getName());
        existingHotel.setFeatures(hotelUpdate.getFeatures());
        existingHotel.setZipcode(hotelUpdate.getZipcode());
        existingHotel.setCity(hotelUpdate.getCity());
        existingHotel.setStreet(hotelUpdate.getStreet());
        existingHotel.setContact(hotelUpdate.getContact());

        return hotelRepository.saveAndFlush(existingHotel);
    }


    /**
     * Finds a hotel with given id
     *
     * @param hotelId the hotel id
     * @return a hotel with given id or HotelNotFoundException when no hotel with given id exists
     */
    @Transactional
    public Hotel findHotelById(long hotelId) {
        log.info("Received request to get the hotel with id {}", hotelId);
        return hotelRepository.findOneById(hotelId).orElseThrow(HotelNotFoundException::new);
    }


    /**
     * Determines whether a hotel with given id exists
     *
     * @param hotelId the hotel id
     * @return true, if a hotel with given id could be found, else false
     */
    @Transactional
    public boolean existsHotelWithId(long hotelId) {
        return hotelRepository.existsHotelById(hotelId);
    }


    /**
     * Gets the average rating of a given hotel
     *
     * @param hotelId the hotel id
     * @return the average rating for the hotel or HotelNotFoundException when no hotel with given id could be found
     */
    @Transactional
    public Double getHotelAverageRating(long hotelId) {
        log.info("Received request for the rating of the hotel with id {}", hotelId);
        Hotel hotel = hotelRepository.findOneById(hotelId).orElseThrow(HotelNotFoundException::new);
        return ratingService.calculateHotelAverageRating(hotel.getRatings());
    }


    /**
     * Retrieves all hotels in a city
     *
     * @param city the city to filter for
     * @return a list with all hotels in a city
     */
    @Transactional
    public List<Hotel> retrieveAllHotelsIn(String city) {
        log.info("Received request for all hotels in {}", city);
        return hotelRepository.findAllByCity(city);
    }


    /**
     * Retireves all hotels in a coty that have an average rating better than a minimum
     *
     * @param city the city to filter for
     * @param minimumRating the minimum rating
     * @return a list with all hotels in the city that have an average rating better than a minimum
     */
    @Transactional
    public List<Hotel> retrieveAllHotelsInCityWithRatingBetterThan(String city, int minimumRating) {
        List<Hotel> hotels = retrieveAllHotelsIn(city);
        return hotels.stream().filter(h -> hasHotelRatingBetterThan(h, minimumRating)).collect(Collectors.toList());
    }


    private boolean hasHotelRatingBetterThan(Hotel hotel, int minimumRating) {
        return ratingService.calculateHotelAverageRating(hotel.getRatings()) > minimumRating;
    }


    private boolean isUserAllowedToUpdateHotel(Hotel hotel, User user) {
        return Objects.equals(hotel.getContact(), user);
    }
}
