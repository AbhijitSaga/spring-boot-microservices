package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.services.UserServices;
import com.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Getter;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServices userServices;

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @PostMapping
    public ResponseEntity<User>  createUser(@RequestBody User user){
        String randomUserId= UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        User user1= userServices.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
int retryCount=1;
    @GetMapping("/{userId}")
   //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
   // @Retry(name = "ratingHotelService",fallbackMethod ="ratingHotelFallback" )
    @RateLimiter(name = "userRateLimiter",fallbackMethod ="ratingHotelFallback" )
    public  ResponseEntity<User> getSingleUser(@PathVariable String userId ){
        logger.info("retry count{}",retryCount);
        retryCount++;

        User user1=userServices.getUser(userId);
        return  ResponseEntity.ok(user1);
    }
//creating fallback method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallback(String userId , Exception ex){
        logger.info("Fallback is executed because service is down: "+ex.getMessage());
      User user=  User.builder().
                email("dummy@gmail.com")
                .name("Dummay")
                .about("This user is created dummy because some services is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
      List<User> users=userServices.getAllUser();
    return   ResponseEntity.ok(users);
    }

}
