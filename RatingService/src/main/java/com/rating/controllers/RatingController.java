package com.rating.controllers;

import com.rating.entities.Rating;
import com.rating.services.RatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingServices ratingServices;

    //create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
       return ResponseEntity.status(HttpStatus.CREATED).body(ratingServices.create(rating));
    }
    @GetMapping
    public  ResponseEntity<List<Rating>> getRatings(){
        return  ResponseEntity.ok(ratingServices.getRatings());
    }
    @GetMapping("/users/{userId}")
    public  ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId){
        return  ResponseEntity.ok(ratingServices.getRatingByUserId(userId));
    }
    @GetMapping("/hotels/{hotelId}")
    public  ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId){
        return  ResponseEntity.ok(ratingServices.getRatingByHotelId(hotelId));
    }
}
