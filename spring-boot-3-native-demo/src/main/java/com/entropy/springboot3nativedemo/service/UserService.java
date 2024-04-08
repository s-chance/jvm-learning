package com.entropy.springboot3nativedemo.service;

import com.entropy.springboot3nativedemo.entity.User;
import com.entropy.springboot3nativedemo.entity.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDetails> getUserDetails();

    List<User> getUsers();
}
