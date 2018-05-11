package de.maibornwolff.ste.bookingham_palace.rating.api.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.rating.model.Rating;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingRequest;
import de.maibornwolff.ste.bookingham_palace.hotel.service.HotelService;
import de.maibornwolff.ste.bookingham_palace.rating.model.RatingResponse;

@Service
public class RatingMapper {

    private final HotelService hotelService;

    public RatingMapper(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    public Rating ratingRequestToRating(RatingRequest ratingRequest) {
        Rating rating = new Rating();
        rating.setHotel(hotelService.findHotelById(ratingRequest.getHotelId()));
        rating.setRatingCategory(ratingRequest.getRating());
        rating.setComment(ratingRequest.getComment());
        return rating;
    }


    public List<RatingResponse> ratingsToRatingResponses(List<Rating> ratings) {
        return ratings.stream().map(this::ratingToRatingResponse).collect(Collectors.toList());
    }


    private RatingResponse ratingToRatingResponse(Rating rating) {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setId(rating.getId());
        ratingResponse.setHotel(rating.getHotel().getName());
        ratingResponse.setComment(rating.getComment());
        ratingResponse.setAuthor(rating.getAuthor().getUsername());
        ratingResponse.setPublishDate(rating.getPublishDate());
        ratingResponse.setRatingCategory(rating.getRatingCategory());
        return ratingResponse;
    }

}
