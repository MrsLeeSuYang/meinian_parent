package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.constant.MessageConstant;
import com.cxy.dao.MemberDao;
import com.cxy.dao.OrderDao;
import com.cxy.dao.OrderSettingDao;
import com.cxy.entity.Result;
import com.cxy.pojo.Member;
import com.cxy.pojo.Order;
import com.cxy.pojo.OrderSetting;
import com.cxy.service.OrderService;
import com.cxy.util.DateUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public Map<String, Object> findById(Integer orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public Result saveOrder(Map map) throws Exception {
        int setmealId = Integer.parseInt((String)map.get("setmealId"));
        String orderDate = (String)map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);

        OrderSetting orderSetting = orderSettingDao.getOrderSettingByOrderDate(date);
        if(orderSetting == null){//预约设置不存在，不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if(reservations >= number){
                return new Result(false,MessageConstant.ORDER_FULL);
            }
        }

        String telephone = (String)map.get("telephone");
        Member member = memberDao.getMemberByTelephone(telephone);
        if(member == null){
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setIdCard((String)map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberDao.add(member);
        }else {
            Order orderParam = new Order();
            orderParam.setOrderDate(date);
            orderParam.setSetmealId(setmealId);
            orderParam.setMemberId(member.getId());
            List<Order> orderList = orderDao.findOrderByCondition(orderParam);

            if(orderList != null && orderList.size() > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }

        Order order = new Order();
        order.setMemberId(member.getId());

        order.setOrderDate(date);
        order.setOrderType("微信预约");
        order.setOrderStatus("未出游");
        order.setSetmealId(setmealId);
        orderDao.add(order);

        orderSetting.setReservations(orderSetting.getReservations()+1);

        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }
}