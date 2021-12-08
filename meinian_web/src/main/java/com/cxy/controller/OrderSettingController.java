package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.entity.Result;
import com.cxy.pojo.OrderSetting;
import com.cxy.service.OrderSettingService;
import com.cxy.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody Map map){
        try {
            orderSettingService.editNumberByOrderDate(map);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);

            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);

            List<OrderSetting> listData = new ArrayList<>();

            for (String[] strArray : list) {
                String dateStr = strArray[0];
                String numberStr = strArray[1];
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(dateStr));
                orderSetting.setNumber(Integer.parseInt(numberStr));
                listData.add(orderSetting);
            }

            orderSettingService.addBatch(listData);

            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPLOAD_FAIL);
        }

    }
}