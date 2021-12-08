package com.cxy.dao;

import com.cxy.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

    long findCountByTravelitemId(Integer id);

    /**
     * 帮助封装跟团游的travelItems属性方法
     * */
    List<TravelItem> findTravelItemById(Integer id);
}