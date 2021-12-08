package com.cxy.dao;

import com.cxy.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndTravelGroup(Map<String, Integer> map);

    Page findPage(String queryString);

    void delete(Integer id);

    void deletetcy(Integer id);

    Setmeal getById(Integer id);

    List<Integer> getTravelGroupIdsBySetmealId(Integer setmealId);

    List getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getSetmealById(Integer id);

    List<Map> getSetmealReport();
}