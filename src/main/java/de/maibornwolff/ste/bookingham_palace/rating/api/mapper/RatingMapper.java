package de.maibornwolff.ste.bookingham_palace.rating.api.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingRequest;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingResponse;

/**
 * Mapper to map all the rating related requests and responses
 */
@Service
public class RatingMapper {

    private final HotelService hotelService;

    public RatingMapper(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    /**
     * Maps a rating request to the rating entity
     *
     * @param ratingRequest the rating request
     * @return an object of rating
     */
    public Rating ratingRequestToRating(RatingRequest ratingRequest) {
        Rating rating = new Rating();
        rating.setHotel(hotelService.findHotelById(ratingRequest.getHotelId()));
        rating.setRatingCategory(ratingRequest.getRating());
        rating.setComment(ratingRequest.getComment());
        return rating;
    }


    /**
     * Maps a rating to a rating response
     *
     * @param rating a rating
     * @return a rating response for the given rating
     */
    public RatingResponse ratingToRatingResponse(Rating rating) {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setId(rating.getId());
        ratingResponse.setHotel(rating.getHotel().getName());
        ratingResponse.setComment(rating.getComment());
        ratingResponse.setAuthor(rating.getAuthor().getUsername());
        ratingResponse.setPublishDate(rating.getPublishDate());
        ratingResponse.setRatingCategory(rating.getRatingCategory());
        return ratingResponse;
    }


    /**
     * Maps a list of ratings to rating responses
     *
     * @param ratings a list of ratings
     * @return a list of rating responses
     */
    public List<RatingResponse> ratingsToRatingResponses(List<Rating> ratings) {
        return ratings.stream().map(this::ratingToRatingResponse).collect(Collectors.toList());
    }
}
