package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.dao.UserDao;
import com.cxy.pojo.User;
import com.cxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}