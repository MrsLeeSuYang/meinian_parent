package com.cxy.service;

import com.cxy.entity.PageResult;
import com.cxy.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {

    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    TravelGroup getById(Integer id);

    List<Integer> getTravelitemIdsByTravelGroupId(Integer travelGroupId);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    List<TravelGroup> findAll();

    void delete(Integer id);
}