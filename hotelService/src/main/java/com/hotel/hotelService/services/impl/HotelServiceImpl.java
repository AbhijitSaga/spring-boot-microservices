package com.hotel.hotelService.services.impl;

import com.hotel.hotelService.entites.Hotel;
import com.hotel.hotelService.exceptions.ResourceNotFoundException;
import com.hotel.hotelService.repositories.HotelRepository;
import com.hotel.hotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {
        String hotelId= UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return  hotelRepository.saveAndFlush(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found!!"));
    }
}
