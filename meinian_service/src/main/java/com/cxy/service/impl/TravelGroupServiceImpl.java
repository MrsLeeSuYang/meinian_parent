package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.dao.TravelGroupDao;
import com.cxy.entity.PageResult;
import com.cxy.pojo.TravelGroup;
import com.cxy.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public void delete(Integer id) {
        //先删除中间表的关联数据
        travelGroupDao.delete(id);

        //再删除
        travelGroupDao.deletegty(id);
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.edit(travelGroup);
        Integer travelGroupId = travelGroup.getId();

        //先删除中间表的关联数据
        travelGroupDao.delete(travelGroupId);

        //重新再增加关联数据
        setTravelGroupAndTravelItem(travelGroupId,travelItemIds);
    }

    @Override
    public List<Integer> getTravelitemIdsByTravelGroupId(Integer travelGroupId) {
        return travelGroupDao.getTravelitemIdsByTravelGroupId(travelGroupId);
    }

    @Override
    public TravelGroup getById(Integer id) {
        return travelGroupDao.getById(id);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);//数据库分配id
        Integer travelGroupId = travelGroup.getId();//id能拿到吗？不能，必须进行主键回填
        setTravelGroupAndTravelItem(travelGroupId,travelItemIds);
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if(travelItemIds!=null && travelItemIds.length>0){
            //准备dao层需要参数，利用Map集合作为参数传递数据

            for (Integer travelItemId : travelItemIds) {
                Map<String,Integer> paramData = new HashMap<>();
                paramData.put("travelGroupId",travelGroupId);
                paramData.put("travelItemId",travelItemId);
                travelGroupDao.addTravelGroupAndTravelItem(paramData);
            }
        }
    }
}