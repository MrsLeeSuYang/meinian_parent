package com.cxy.dao;

import com.cxy.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}