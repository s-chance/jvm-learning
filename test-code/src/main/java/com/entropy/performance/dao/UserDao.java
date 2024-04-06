package com.entropy.performance.dao;

import com.entropy.performance.entity.UserDetails;

import java.util.List;

public interface UserDao {

    List<UserDetails> findUsers();
}
