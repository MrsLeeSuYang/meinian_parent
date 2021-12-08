package com.cxy.service;

import com.cxy.entity.PageResult;
import com.cxy.entity.QueryPageBean;
import com.cxy.pojo.Address;
import java.util.List;

public interface AddressService {

    List<Address> findAllMaps();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Address address);

    void deleteById(Integer id);
}
