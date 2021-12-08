package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.constant.RedisConstant;
import com.cxy.dao.SetmealDao;
import com.cxy.entity.PageResult;
import com.cxy.pojo.Setmeal;
import com.cxy.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public List<Map> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }

    @Override
    public Setmeal getSetmealById(Integer id) {
        return setmealDao.getSetmealById(id);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public List<Integer> getTravelGroupIdsBySetmealId(Integer setmealId) {
        return setmealDao.getTravelGroupIdsBySetmealId(setmealId);
    }

    @Override
    public Setmeal getById(Integer id) {
        return setmealDao.getById(id);
    }

    @Override
    public void delete(Integer id) {
        //先删除关联表数据
        setmealDao.delete(id);

        //再删除
        setmealDao.deletetcy(id);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        //1.保存套餐
        setmealDao.add(setmeal);//主键回填

        //2.保存关联数据
        Integer setmealId = setmeal.getId();
        setSetmealAndTravelGroup(travelgroupIds,setmealId);

        String pic = setmeal.getImg();

        //*************************补充代码，解决七牛云上“垃圾图片”问题************************
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    private void setSetmealAndTravelGroup(Integer[] travelgroupIds, Integer setmealId) {
        if(travelgroupIds!=null && travelgroupIds.length>0){
            for (Integer travelgroupId : travelgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("travelgroupId",travelgroupId);
                map.put("setmealId",setmealId);
                setmealDao.addSetmealAndTravelGroup(map);
            }
        }
    }
}