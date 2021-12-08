package com.cxy.service;

import com.cxy.entity.PageResult;
import com.cxy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    Setmeal getById(Integer id);

    List<Integer> getTravelGroupIdsBySetmealId(Integer setmealId);

    List getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getSetmealById(Integer id);

    List<Map> getSetmealReport();
}