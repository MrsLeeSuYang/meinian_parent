package com.cxy.service;

import com.cxy.entity.PageResult;
import com.cxy.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}