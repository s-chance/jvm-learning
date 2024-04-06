package com.entropy.performance.service;

import com.entropy.performance.entity.User;
import com.entropy.performance.entity.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDetails> getUserDetails();

    List<User> getUsers();
}
