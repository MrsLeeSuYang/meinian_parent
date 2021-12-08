package com.cxy.dao;

import com.cxy.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;

public interface AddressDao {
    List<Address> findAllMaps();

    Page findPage(String queryString);

    void add(Address address);

    void deleteById(Integer id);
}
