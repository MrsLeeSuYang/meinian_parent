package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.constant.RedisMessageConstant;
import com.cxy.entity.Result;
import com.cxy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    /**
     *
     * */
    @RequestMapping("/findById")
    public Result findById(Integer orderId){
        try {
            Map<String,Object> map = orderService.findById(orderId);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    /**
     * 由于页面表单数据来自多张表的数据，用pojo对象接收容易接受不完整，所以使用Map接收
     * */
    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map map){
        try {
//            System.out.println("map = " + map);
            String telephone = (String)map.get("telephone");
            String validateCode = (String)map.get("validateCode");
            String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            if(redisCode == null || !redisCode.equals(validateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            Result result = orderService.saveOrder(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FAIL);
        }
    }
}