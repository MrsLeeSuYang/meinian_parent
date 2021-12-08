package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.entity.PageResult;
import com.cxy.entity.QueryPageBean;
import com.cxy.entity.Result;
import com.cxy.pojo.Address;
import com.cxy.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.FacesRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    AddressService addressService;

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            addressService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/addAddress")
    public Result add(@RequestBody Address address){
        try {
            addressService.add(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
        Map map = new HashMap();

        List<Address> list = addressService.findAllMaps();

        List<Map> gridnMaps = new ArrayList<>();//标记地址的经纬度
        List<Map> nameMaps = new ArrayList<>();//标记地址名称

        for (Address address : list) {
            String addressName = address.getAddressName();

            Map<String,String> mapName = new HashMap<>();
            mapName.put("addressName", addressName);
            nameMaps.add(mapName);

            Map<String,String> gridnMap = new HashMap<>();
            gridnMap.put("lng", address.getLng());
            gridnMap.put("lat", address.getLat());
            gridnMaps.add(gridnMap);
        }

        map.put("gridnMaps", gridnMaps);
        map.put("nameMaps", nameMaps);

        return map;
    }
}
