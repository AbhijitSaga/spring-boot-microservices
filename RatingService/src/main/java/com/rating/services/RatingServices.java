package com.rating.services;

import com.rating.entities.Rating;

import java.util.List;

public interface RatingServices {
    //create rating
    Rating create(Rating rating);

    //get all rating
    List<Rating> getRatings();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);
}
