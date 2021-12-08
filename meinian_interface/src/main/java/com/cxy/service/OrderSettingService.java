package com.cxy.service;

import com.cxy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void addBatch(List<OrderSetting> listData);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByOrderDate(Map map);
}