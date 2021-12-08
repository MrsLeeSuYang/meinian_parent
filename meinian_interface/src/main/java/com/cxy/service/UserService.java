package com.cxy.service;

import com.cxy.pojo.User;

public interface UserService {
    User findUserByUsername(String username);
}