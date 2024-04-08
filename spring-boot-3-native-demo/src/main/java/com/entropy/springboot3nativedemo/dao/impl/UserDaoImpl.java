package com.entropy.springboot3nativedemo.dao.impl;

import com.entropy.springboot3nativedemo.dao.UserDao;
import com.entropy.springboot3nativedemo.entity.UserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    // 提前准备好数据
    private List<UserDetails> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        // 初始化时生成数据
        for (int i = 0; i < 10000; i++) {
            users.add(new UserDetails((long) i, LocalDateTime.now(), new Date()));
        }
    }

    /*
        模拟数据库查询接口，主要是只优化Java相关代码，屏蔽SQL相关内容
     */
    @Override
    public List<UserDetails> findUsers() {
        return users;
    }
}
