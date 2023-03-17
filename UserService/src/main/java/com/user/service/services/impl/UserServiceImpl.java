package com.user.service.services.impl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.external.services.HotelService;
import com.user.service.external.services.RatingService;
import com.user.service.repository.UserRepository;
import com.user.service.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
   private UserRepository userRepository;
    @Autowired
    @Qualifier("myRestTemplateBean")
    RestTemplate restTemplate;

    @Autowired
     private HotelService hotelService;
    @Autowired
    RatingService ratingService;

    Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public User getUser(String userId) {

        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId is not found on server!!"+userId));

       /* String url="http://RATING-SERVICE/ratings/users/"+user.getUserId();
        Rating[] ratingOfUser=   restTemplate.getForObject(url, Rating[].class);*/
        Rating[] ratingOfUser  = ratingService.getRating(user.getUserId());

        List<Rating> ratings= Arrays.stream(ratingOfUser).toList();

     List<Rating> ratingList=  ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel

        /* ResponseEntity<Hotel> forEntity =restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
         Hotel hotel=  forEntity.getBody();
       logger.info("response status code:{}",forEntity.getStatusCode());
        */
         Hotel hotel=  hotelService.getHotel(rating.getHotelId());
            //set the hotel to rating
         rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
