package com.entropy.springboot3nativedemo.dao;

import com.entropy.springboot3nativedemo.entity.UserDetails;

import java.util.List;

public interface UserDao {

    List<UserDetails> findUsers();
}
