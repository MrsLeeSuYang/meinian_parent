package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.entity.PageResult;
import com.cxy.entity.QueryPageBean;
import com.cxy.entity.Result;
import com.cxy.pojo.TravelItem;
import com.cxy.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController //组合注解，相当于@Controller+@ResponseBody
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference //远程调用服务
    TravelItemService travelItemService;

    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelItem> list = travelItemService.findAll();
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,list);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("getById")
    public Result getById(Integer id){
        try {
            TravelItem travelItem = travelItemService.getById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result delete(Integer id){//@RequestParam("id")
        try {
            travelItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    //表单项目参数名称必须和实体对象的属性名称一致，提供对应set方法，框架创建对象并封装数据
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    public Result add(@RequestBody TravelItem travelItem){//@RequestBody表示从请求体获取数据
        try {
            travelItemService.add(travelItem);
            return new Result(true,MessageConstant.ADD_TRAVELITEM_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }
}