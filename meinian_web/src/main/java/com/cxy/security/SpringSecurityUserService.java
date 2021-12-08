package com.cxy.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.pojo.Permission;
import com.cxy.pojo.Role;

import com.cxy.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference//远程调用
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询用户信息，以及用户对应的角色，以及角色对应的权限。
        com.cxy.pojo.User user = userService.findUserByUsername(username);

        if(user == null){//如果不存在该用户
            return null;//返回null给框架，框架会抛出异常，进行异常处理，跳转到登录页面。
        }

        //2.构建权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();

        Set<Role> roles = user.getRoles();//集合数据由RoleDao帮忙方法来查询得到

        for (Role role : roles) {//集合数据由PermissionDao帮忙方法来查询得到的
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);

        return securityUser;//框架提供的User实现了UserDetails接口
    }
}