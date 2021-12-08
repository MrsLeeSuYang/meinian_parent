package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.dao.TravelItemDao;
import com.cxy.entity.PageResult;
import com.cxy.pojo.TravelItem;
import com.cxy.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class) //发布服务，注册到zookeeper中心
@Transactional //声明事务，所有方法都增加事务
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    TravelItemDao travelItemDao;

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public TravelItem getById(Integer id) {
        return travelItemDao.getById(id);
    }

    @Override
    public void delete(Integer id) {
        long count = travelItemDao.findCountByTravelitemId(id);

        if(count > 0){//count > 0表示有关联数据
            throw new RuntimeException("删除自由行失败，因为存在在关联数据，需要先解除关联关系才能删除");
        }
        travelItemDao.delete(id);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //分页插件
        //开启分页功能
        //limit (currentPage-1)*pageSize,pageSize
        PageHelper.startPage(currentPage,pageSize);//limit a,b a表示开始索引，b表示查询的条数
        Page page = travelItemDao.findPage(queryString);//返回当前页数据
        return new PageResult(page.getTotal(),page.getResult());//page.getTotal()表示总记录数，page.getResult()表示分页数据集合
    }

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

}