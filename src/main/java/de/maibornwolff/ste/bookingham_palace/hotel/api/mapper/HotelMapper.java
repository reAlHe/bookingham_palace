package de.maibornwolff.ste.bookingham_palace.hotel.api.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import de.maibornwolff.ste.bookingham_palace.hotel.model.Hotel;
import de.maibornwolff.ste.bookingham_palace.hotel.model.HotelRequest;
import de.maibornwolff.ste.bookingham_palace.hotel.model.HotelResponse;
import de.maibornwolff.ste.bookingham_palace.rating.api.mapper.RatingMapper;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;

@Service
public class HotelMapper {

    private final UserService userService;

    private final RatingMapper ratingMapper;


    public HotelMapper(UserService userService, RatingMapper ratingMapper) {
        this.userService = userService;
        this.ratingMapper = ratingMapper;
    }


    public Hotel mapHotelRequestToHotel(HotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setStreet(request.getStreet());
        hotel.setCity(request.getCity());
        hotel.setZipcode(request.getZipcode());
        hotel.setContact(userService.findUserByUsername(request.getContact()));
        hotel.setFeatures(request.getFeatures());
        return hotel;
    }


    public HotelResponse mapHotelToHotelResponse(Hotel hotel){
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setId(hotel.getId());
        hotelResponse.setName(hotel.getName());
        hotelResponse.setStreet(hotel.getStreet());
        hotelResponse.setCity(hotel.getCity());
        hotelResponse.setZipcode(hotel.getZipcode());
        hotelResponse.setContact(hotel.getContact().getUsername());
        hotelResponse.setRatings(ratingMapper.ratingsToRatingResponses(hotel.getRatings()));
        hotelResponse.setFeatures(hotel.getFeatures());
        return hotelResponse;
    }


    public List<HotelResponse> mapHotelsToHotelResponses(List<Hotel> hotels) {
        return hotels.stream().map(this::mapHotelToHotelResponse).collect(Collectors.toList());
    }

}
