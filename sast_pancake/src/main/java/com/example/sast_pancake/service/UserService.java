package com.example.sast_pancake.service;

import com.example.sast_pancake.pojo.User;


public interface UserService {

    User login(User user);
    User getById(Integer integer);

    User register(User user);
}
