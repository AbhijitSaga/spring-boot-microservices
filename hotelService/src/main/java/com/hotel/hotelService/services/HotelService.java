package com.hotel.hotelService.services;

import com.hotel.hotelService.entites.Hotel;

import java.util.List;

public interface HotelService {

    public Hotel create(Hotel hotel);
    public List<Hotel> getAll();
    public Hotel  get(String id);
}
