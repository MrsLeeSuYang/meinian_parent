package com.cxy.dao;

import com.cxy.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {

    void add(TravelGroup travelGroup);

    void addTravelGroupAndTravelItem(Map<String, Integer> paramData);

    Page findPage(@Param("queryString") String queryString);

    TravelGroup getById(Integer id);

    List<Integer> getTravelitemIdsByTravelGroupId(Integer travelGroupId);

    void edit(TravelGroup travelGroup);

    void delete(Integer travelGroupId);

    List<TravelGroup> findAll();

    void deletegty(Integer id);

    /**
     * 帮助封装套餐对象的travelGroups属性方法
     * */
    List<TravelGroup> findTravelGroupById(Integer id);
}