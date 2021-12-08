package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.entity.Result;
import com.cxy.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    //获取当前登录用户的用户名
    @RequestMapping("/getUsername")
    public Result getUsername()throws Exception{
        try{
            //框架认证成功，会将用户信息放在session域中，同时将用户信息绑定到当前线程上
            //可以通过SecurityContext来获取认证信息
            //Authentication 认证对象
            //Principal 认证主体，其实就是自己的user对象
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
