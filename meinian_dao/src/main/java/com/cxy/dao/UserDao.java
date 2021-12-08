package com.cxy.dao;

import com.cxy.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);
}