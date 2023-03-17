package com.user.service.services;

import com.user.service.entities.User;

import java.util.List;

public interface UserServices {
    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);
}
