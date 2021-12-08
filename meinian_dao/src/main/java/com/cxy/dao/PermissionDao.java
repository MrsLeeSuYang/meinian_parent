package com.cxy.dao;

import com.cxy.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}